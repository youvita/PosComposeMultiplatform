package pin.utils

import core.utils.SharePrefer


object PinManager {

    private const val PIN_LOCK = "pin_lock"

    /**
     * Joins list of int to String.
     *
     * @param pin
     * List of numbers.
     *
     * @return joined pin of string.
     *
     * @throws IllegalStateException
     * If list size does not match required pin length.
     */
    private fun fromIntList(pin: List<Int>): String {
        if (pin.size != PinConst.PIN_LENGTH) throw IllegalStateException("Pin size does not match length. Actual: ${pin.size}. Expected: ${PinConst.PIN_LENGTH}")
        return pin.joinToString { it.toString() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // INTERNAL API
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Saves the pin in encrypted SharedPreferences.
     *
     * @param pin
     * List of pin numbers.
     */
    internal fun savePin(pin: List<Int>) {
        SharePrefer.putPrefer(PIN_LOCK, fromIntList(pin))
    }

    /**
     * Checks the passed pin with saved pin.
     *
     * @return true if passed pin matches exactly as saved pin, false if pin does not match saved pin.
     *
     * @param pin
     * List of pin numbers.
     */
    internal fun checkPin(pin: List<Int>): Boolean {
        if (!pinExists()) return false
        val savedPin = SharePrefer.getPrefer(PIN_LOCK)
        return savedPin == fromIntList(pin)
    }


    /**
     * Checks if there is already saved pin.
     *
     * @return true if there is already saved pin, false if there is no saved pin.
     */
    fun pinExists(): Boolean {
        return (SharePrefer.getPrefer(PIN_LOCK) != "" || !SharePrefer.getPrefer(PIN_LOCK).isNullOrBlank())
    }

    /**
     * Clears the saved pin. By calling this function, you can clear the saved pin so that user can create a new pin without remembering
     * the saved pin.
     */
    fun clearPin() {
        SharePrefer.putPrefer(PIN_LOCK, "")
    }
}