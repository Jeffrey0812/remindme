#include <string.h>
#include <stdio.h>
#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_jeffrey_remindme_ui_CategoryListActivity_errorFromJNI( JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Network connection is not available, unable to save category");
}