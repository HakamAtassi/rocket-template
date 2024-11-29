package gcd

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage


import freechips.rocketchip.subsystem._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket._


import org.chipsalliance.cde.config.Parameters


import freechips.rocketchip.amba.AMBAProtField
import freechips.rocketchip.diplomacy.{IdRange, TransferSizes, RegionType}
import freechips.rocketchip.tile.{L1CacheParams, HasL1CacheParameters, HasCoreParameters, CoreBundle, HasNonDiplomaticTileParameters, BaseTile, HasTileParameters}
import freechips.rocketchip.tilelink.{TLMasterParameters, TLClientNode, TLManagerNode, TLMasterPortParameters, TLEdgeOut, TLWidthWidget, TLFIFOFixer, ClientMetadata, TLClientParameters, TLSlavePortParameters, TLManagerParameters}
import freechips.rocketchip.util.{Code, RandomReplacement, ParameterizedBundle}


object main extends App {
  ChiselStage.emitSystemVerilogFile(
    new GCD,
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )



  implicit val p: Parameters = Parameters.empty

  ChiselStage.emitSystemVerilogFile(
    LazyModule(new MyClientManagerWrapper).module,
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )

    //ChiselStage.emitSystemVerilogFile(new axi_ram_wrap(nocParameters), Array("--split-verilog", 
                                                                        //"--target", "verilog", 
                                                                        //"--target-dir", "../verilog", 
                                                                        //"--preserve-aggregate", "all", 
                                                                        //), 
                                                                        //firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
                                                                        //)

}
