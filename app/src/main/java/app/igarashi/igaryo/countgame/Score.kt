package app.igarashi.igaryo.countgame

import io.realm.RealmObject

open class Score(
    open var second: Double = 0.0
):RealmObject() {
}