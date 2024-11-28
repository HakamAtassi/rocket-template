package gcd

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

import chisel3.util._

import org.chipsalliance.cde.config._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket._
import freechips.rocketchip.util._

// Client sends a counter every cycle 
// Manager ACKs counter when received (every cycle)

// Sends requests on A, recieves on D
class MyClient(implicit p: Parameters) extends LazyModule {
  val node = TLClientNode(Seq(TLMasterPortParameters.v1(Seq(TLClientParameters(
    name = "my-client",
    sourceId = IdRange(0, 1),
    requestFifo = true,
    visibility = Seq(AddressSet(0x20000, 0xffff)))))))

    lazy val module = new LazyModuleImp(this) {
        val (tl, edge) = node.out(0)

        // Send a counter every cycle
        val dataCounter = RegInit(1.U(8.W))  // 8-bit data counter
        dataCounter := dataCounter + 1.U

        //// Set up the Put transaction
        tl.a.bits := edge.Put(
            fromSource = 0.U,               // Source ID
            toAddress = 0x20000.U,          // Target address
            lgSize = 0.U,
            data = dataCounter              // Data to send
        )._2

        tl.a.valid := 1.B
        tl.d.ready := 1.B

        // Check if the transaction is accepted
        when(tl.a.fire) {
            printf("Sent data: 0x%x to address: 0x%x\n", dataCounter, tl.a.bits.address)
        }
    }
}

// recieves requests on A, returns on D
class MyManager(implicit p: Parameters) extends LazyModule {
  val device = new SimpleDevice("my-device", Seq("tutorial,my-device0"))
  val beatBytes = 1
  val node = TLManagerNode(Seq(TLSlavePortParameters.v1(Seq(TLManagerParameters(
    address = Seq(AddressSet(0x20000, 0xfff)),
    resources = device.reg,
    regionType = RegionType.UNCACHED,
    executable = true,
    supportsArithmetic = TransferSizes(1, beatBytes),
    supportsLogical = TransferSizes(1, beatBytes),
    supportsGet = TransferSizes(1, beatBytes),
    supportsPutFull = TransferSizes(1, beatBytes),
    supportsPutPartial = TransferSizes(1, beatBytes),
    supportsHint = TransferSizes(1, beatBytes),
    fifoId = Some(0))), beatBytes)))

    lazy val module = new LazyModuleImp(this) {
        val (tl, edge) = node.in(0)

        // Ack each write. 
        tl.d.bits  := edge.AccessAck(tl.a.bits)
        tl.d.valid := tl.a.valid

        tl.a.ready := 1.B   // always ready to receive  

        when(tl.d.fire) {
            printf("Received data: 0x%x\n", tl.a.bits.data)
        }
    }
}

class MyClientManagerWrapper(implicit p: Parameters) extends LazyModule {
    val client = LazyModule(new MyClient)
    val manager = LazyModule(new MyManager)

    // Connect client to manager
    manager.node := client.node
    lazy val module = new LazyModuleImp(this) {
    }
}
