package phamf.com.chemicalapp.Database

import android.content.Context

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons

class OfflineDatabaseManager(context: Context) {

    private var realm: Realm

    init {
        Realm.init(context)
        var configuration = RealmConfiguration.Builder().name("database.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.getInstance(configuration)
        Realm.setDefaultConfiguration(configuration)
        realm = Realm.getDefaultInstance()
    }

    fun <E : RealmObject> readAllDataOf(dataType: Class<E>): RealmResults<E> {
        return realm.where(dataType).findAll()
    }

    fun <E : RealmObject> readSomeDataOf(dataType: Class<E>, whereField: String, value: Int): RealmResults<E> {
        return realm.where(dataType).equalTo(whereField, value).findAll()
    }

    fun <E : RealmObject> readOneObjectOf(dataType: Class<E>, whereField: String, value: Int): E? {
        return realm.where(dataType).equalTo(whereField, value).findFirst()
    }

    fun <E : RealmObject> readOneObjectOf(dataType: Class<E>, whereField: String, value: String): E? {
        return realm.where(dataType).equalTo(whereField, value).findFirst()
    }

    fun <E : RealmObject> addOrUpdateDataOf(dataType: Class<E>, value: Collection<E>) {
        realm.beginTransaction()
        realm.insertOrUpdate(value)
        realm.commitTransaction()
    }

    fun <E : RealmObject> addOrUpdateDataOf(dataType: Class<E>, vararg value: E) {
        realm.beginTransaction()
        if (value.size == 1) {
            realm.insertOrUpdate(value[0])
        } else if (value.size > 1) {
            val list = ArrayList<E>()
            for (e in value) {
                list.add(e)
            }
            realm.insertOrUpdate(list)
        }
        realm.commitTransaction()
    }

    fun <E : RealmObject> deleteAllDataOf(dataType: Class<E>) {
        val results = realm.where(dataType).findAll()
        results.deleteAllFromRealm()
    }

    fun <E : RealmObject> deleteSomeDataOf(dataType: Class<E>, field: String, value: Int) {
        val results = realm.where(dataType).equalTo(field, value).findAll()
        results.deleteAllFromRealm()
    }

    fun <E : RealmObject> readOneOf(dataType: Class<E>): E? {
        return realm.where(dataType).findFirst()
    }

    fun <E : RealmObject> readAsyncOneOf(dataType: Class<E>, querySucessListener: RealmChangeListener<E>): E {
        val result = realm.where(dataType).findFirstAsync()
        result.addChangeListener(querySucessListener)
        return result
    }

    fun beginTransaction() {
        realm.beginTransaction()
    }

    fun commitTransaction() {
        realm.commitTransaction()
    }

    fun <E : RealmObject> readAsyncAllDataOf(dataType: Class<E>, realmChangeListener: RealmChangeListener<RealmResults<E>>): RealmResults<E> {
        val results = realm.where(dataType).findAllAsync()
        results.addChangeListener(realmChangeListener)
        return results
    }


    //    @Override
    //    public RealmResults<RO_Chapter> readList(String field, int value) {
    //        RealmResults<RO_Chapter> results = realm.where(RO_Chapter.class).equalTo(field, value).findAll();
    //        return results;
    //    }
    //
    //
    //    @Override
    //    public void addOrUpdate(Collection<RO_Chapter> chapters) {
    //        realm.beginTransaction();
    //        realm.copyToRealmOrUpdate(chapters);
    //        realm.commitTransaction();
    //    }
    //
    //
    //    @Override
    //    public void addOrUpdate(RO_Chapter chapter) {
    //        realm.beginTransaction();
    //        realm.copyToRealmOrUpdate(chapter);
    //        realm.commitTransaction();
    //    }
    //
    //
    //    @Override
    //    public void delete(String field, int value) {
    //        RealmResults<RO_Chapter> chapters = readList(field, value);
    //        chapters.deleteAllFromRealm();
    //    }
    //
    //
    //    @Override
    //    public void close() {
    //        realm.close();
    //    }
    //
    //    @Override
    //    public RealmResults<RO_Chapter> getAll() {
    //        return realm.where(RO_Chapter.class).findAll();
    //    }

}
