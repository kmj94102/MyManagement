package com.example.mymanagement.kakao_api.model

//import android.content.Context
//import androidx.datastore.core.CorruptionException
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.Serializer
//import androidx.datastore.dataStore
//import com.example.mymanagement.OnLoginState
//import com.google.protobuf.InvalidProtocolBufferException
//import java.io.InputStream
//import java.io.OutputStream
//
//object OnLoginStateSerializer : Serializer<OnLoginState> {
//    override val defaultValue: OnLoginState = OnLoginState.getDefaultInstance()
//
//    override suspend fun readFrom(input: InputStream): OnLoginState {
//        try {
//            return OnLoginState.parseFrom(input)
//        } catch (e: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto", e)
//        }
//    }
//
//    override suspend fun writeTo(t: OnLoginState, output: OutputStream) = t.writeTo(output)
//}
//
//val Context.storeOnLoginState: DataStore<OnLoginState> by dataStore(
//    fileName = "myManagementLoginSetting.pb",
//    serializer = OnLoginStateSerializer
//)