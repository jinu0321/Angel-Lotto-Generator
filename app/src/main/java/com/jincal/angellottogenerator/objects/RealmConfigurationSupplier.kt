package com.jincal.angellottogenerator.objects

import com.jincal.angellottogenerator.classes.LottoRealmObject
import com.jincal.angellottogenerator.classes.MyLottoRealmObject
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

object RealmConfigurationSupplier {

    @RealmModule(classes = [LottoRealmObject::class])
    class LottoRealmObjectModule

    @RealmModule(classes = [MyLottoRealmObject::class])
    class MyLottoRealmObjectModule

    val lottoApiConfiguration: RealmConfiguration = RealmConfiguration.Builder()
        .schemaVersion(0)
        .assetFile("lottoapi.realm")
        .name("lottoapi.realm")
        .modules(LottoRealmObjectModule())
        .build()

    val myLottoConfiguration: RealmConfiguration = RealmConfiguration.Builder()
        .schemaVersion(0)
        .name("mylotto.realm")
        .modules(MyLottoRealmObjectModule())
        .build()

}