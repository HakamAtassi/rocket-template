// See README.md for license details.

package gcd


import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers


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




/** 
  * TileLink Spec: Verifies the interaction between TileLink Client and Manager.
  * To run the test:
  * 
  * sbt 'testOnly tilelink.TileLinkSpec'
  */


//class GCDSpec extends AnyFreeSpec with Matchers {

  //"Gcd should calculate proper greatest common denominator" in {
    //simulate(new DecoupledGcd(16)) { dut =>
      //val testValues = for { x <- 0 to 10; y <- 0 to 10} yield (x, y)
      //val inputSeq = testValues.map { case (x, y) => (new GcdInputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U) }
      //val resultSeq = testValues.map { case (x, y) =>
        //(new GcdOutputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U, _.gcd -> BigInt(x).gcd(BigInt(y)).U)
      //}

import org.chipsalliance.cde.config.{Config, Parameters}



class TileLinkSpec extends AnyFreeSpec with Matchers {
  implicit val p: Parameters = Parameters.empty

  "allow the client to write sequential data and the manager to receive it" in {
    val lazyModule = LazyModule(new MyClientManagerWrapper)

    simulate((lazyModule.module)){ dut =>
      // Initialize
      dut.reset.poke(true.B)
      dut.clock.step(5)
      dut.reset.poke(false.B)

      // Monitor for completion
      var cycles = 0
      val maxCycles = 1000

      while (cycles < maxCycles) {
        dut.clock.step()
        cycles += 1
      }

    }
  }
}
