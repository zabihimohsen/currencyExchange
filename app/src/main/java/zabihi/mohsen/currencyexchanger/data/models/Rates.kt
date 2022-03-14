package zabihi.mohsen.currencyexchanger.data.models

import com.google.gson.annotations.SerializedName


data class Rates(
    @SerializedName("AED")
    val aED: Double,
//    val aFN: Double,
//    val aLL: Double,
//    val aMD: Double,
//    val aNG: Double,
//    val aOA: Double,
//    val aRS: Double,
//    val aUD: Double,
//    val aWG: Double,
//    val aZN: Double,
//    val bAM: Double,
//    val bBD: Double,
//    val bDT: Double,
//    val bGN: Double,
//    val bHD: Double,
//    val bIF: Double,
//    val bMD: Double,
//    val bND: Double,
//    val bOB: Double,
//    val bRL: Double,
//    val bSD: Double,
//    val bTC: Double,
//    val bTN: Double,
//    val bWP: Double,
//    val bYN: Double,
//    val bYR: Double,
//    val bZD: Double,
//    val cAD: Double,
//    val cDF: Double,
//    val cHF: Double,
//    val cLF: Double,
//    val cLP: Double,
//    val cNY: Double,
//    val cOP: Double,
//    val cRC: Double,
//    val cUC: Double,
//    val cUP: Double,
//    val cVE: Double,
//    val cZK: Double,
//    val dJF: Double,
//    val dKK: Double,
//    val dOP: Double,
//    val dZD: Double,
//    val eGP: Double,
//    val eRN: Double,
//    val eTB: Double,
    @SerializedName("EUR")
    val eUR: Int,
//    @SerializedName("FJD")
//    val fJD: Double,
//    @SerializedName("FKP")
//    val fKP: Double,
//    @SerializedName("GBP")
//    val gBP: Double,
//    @SerializedName("GEL")
//    val gEL: Double,
//    @SerializedName("GGP")
//    val gGP: Double,
//    @SerializedName("GHS")
//    val gHS: Double,
//    @SerializedName("GIP")
//    val gIP: Double,
//    @SerializedName("GMD")
//    val gMD: Double,
//    @SerializedName("GNF")
//    val gNF: Double,
//    @SerializedName("GTQ")
//    val gTQ: Double,
//    @SerializedName("GYD")
//    val gYD: Double,
//    @SerializedName("HKD")
//    val hKD: Double,
//    @SerializedName("HNL")
//    val hNL: Double,
//    @SerializedName("HRK")
//    val hRK: Double,
//    @SerializedName("HTG")
//    val hTG: Double,
//    @SerializedName("HUF")
//    val hUF: Double,
//    @SerializedName("IDR")
//    val iDR: Double,
//    @SerializedName("ILS")
//    val iLS: Double,
//    @SerializedName("IMP")
//    val iMP: Double,
//    @SerializedName("INR")
//    val iNR: Double,
//    @SerializedName("IQD")
//    val iQD: Double,
//    @SerializedName("IRR")
//    val iRR: Double,
//    @SerializedName("ISK")
//    val iSK: Double,
//    @SerializedName("JEP")
//    val jEP: Double,
//    @SerializedName("JMD")
//    val jMD: Double,
//    @SerializedName("JOD")
//    val jOD: Double,
//    @SerializedName("JPY")
//    val jPY: Double,
//    @SerializedName("KES")
//    val kES: Double,
//    @SerializedName("KGS")
//    val kGS: Double,
//    @SerializedName("KHR")
//    val kHR: Double,
//    @SerializedName("KMF")
//    val kMF: Double,
//    @SerializedName("KPW")
//    val kPW: Double,
//    @SerializedName("KRW")
//    val kRW: Double,
//    @SerializedName("KWD")
//    val kWD: Double,
//    @SerializedName("KYD")
//    val kYD: Double,
//    @SerializedName("KZT")
//    val kZT: Double,
//    @SerializedName("LAK")
//    val lAK: Double,
//    @SerializedName("LBP")
//    val lBP: Double,
//    @SerializedName("LKR")
//    val lKR: Double,
//    @SerializedName("LRD")
//    val lRD: Double,
//    @SerializedName("LSL")
//    val lSL: Double,
//    @SerializedName("LTL")
//    val lTL: Double,
//    @SerializedName("LVL")
//    val lVL: Double,
//    @SerializedName("LYD")
//    val lYD: Double,
//    @SerializedName("MAD")
//    val mAD: Double,
//    @SerializedName("MDL")
//    val mDL: Double,
//    @SerializedName("MGA")
//    val mGA: Double,
//    @SerializedName("MKD")
//    val mKD: Double,
//    @SerializedName("MMK")
//    val mMK: Double,
//    @SerializedName("MNT")
//    val mNT: Double,
//    @SerializedName("MOP")
//    val mOP: Double,
//    @SerializedName("MRO")
//    val mRO: Double,
//    @SerializedName("MUR")
//    val mUR: Double,
//    @SerializedName("MVR")
//    val mVR: Double,
//    @SerializedName("MWK")
//    val mWK: Double,
//    @SerializedName("MXN")
//    val mXN: Double,
//    @SerializedName("MYR")
//    val mYR: Double,
//    @SerializedName("MZN")
//    val mZN: Double,
//    @SerializedName("NAD")
//    val nAD: Double,
//    @SerializedName("NGN")
//    val nGN: Double,
//    @SerializedName("NIO")
//    val nIO: Double,
//    @SerializedName("NOK")
//    val nOK: Double,
//    @SerializedName("NPR")
//    val nPR: Double,
//    @SerializedName("NZD")
//    val nZD: Double,
//    @SerializedName("OMR")
//    val oMR: Double,
//    @SerializedName("PAB")
//    val pAB: Double,
//    @SerializedName("PEN")
//    val pEN: Double,
//    @SerializedName("PGK")
//    val pGK: Double,
//    @SerializedName("PHP")
//    val pHP: Double,
//    @SerializedName("PKR")
//    val pKR: Double,
//    @SerializedName("PLN")
//    val pLN: Double,
//    @SerializedName("PYG")
//    val pYG: Double,
//    @SerializedName("QAR")
//    val qAR: Double,
//    @SerializedName("RON")
//    val rON: Double,
//    @SerializedName("RSD")
//    val rSD: Double,
//    @SerializedName("RUB")
//    val rUB: Double,
//    @SerializedName("RWF")
//    val rWF: Double,
//    @SerializedName("SAR")
//    val sAR: Double,
//    @SerializedName("SBD")
//    val sBD: Double,
//    @SerializedName("SCR")
//    val sCR: Double,
//    @SerializedName("SDG")
//    val sDG: Double,
//    @SerializedName("SEK")
//    val sEK: Double,
//    @SerializedName("SGD")
//    val sGD: Double,
//    @SerializedName("SHP")
//    val sHP: Double,
//    @SerializedName("SLL")
//    val sLL: Double,
//    @SerializedName("SOS")
//    val sOS: Double,
//    @SerializedName("SRD")
//    val sRD: Double,
//    @SerializedName("STD")
//    val sTD: Double,
//    @SerializedName("SVC")
//    val sVC: Double,
//    @SerializedName("SYP")
//    val sYP: Double,
//    @SerializedName("SZL")
//    val sZL: Double,
//    @SerializedName("THB")
//    val tHB: Double,
//    @SerializedName("TJS")
//    val tJS: Double,
//    @SerializedName("TMT")
//    val tMT: Double,
//    @SerializedName("TND")
//    val tND: Double,
//    @SerializedName("TOP")
//    val tOP: Double,
//

//    val tRY: Double,
//    val tTD: Double,
//    val tWD: Double,
//    val tZS: Double,
//    val uAH: Double,
//    val uGX: Double,
    @SerializedName("USD")
    val uSD: Double,
//    val uYU: Double,
//    val uZS: Double,
//    val vEF: Double,
//    val vND: Double,
//    val vUV: Double,
//    val wST: Double,
//    val xAF: Double,
//    val xAG: Double,
//    val xAU: Double,
//    val xCD: Double,
//    val xDR: Double,
//    val xOF: Double,
//    val xPF: Double,
//    val yER: Double,
//    val zAR: Double,
//    val zMK: Double,
//    val zMW: Double,
//    val zWL: Double
)