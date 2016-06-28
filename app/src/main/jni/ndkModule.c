#include <jni.h>


JNIEXPORT jint JNICALL Java_input_CalcManager_nativeMultiply(JNIEnv *env, jobject obj,
                                                             jint a, jint b) {
    return a * b;
}

JNIEXPORT jint JNICALL Java_input_CalcManager_nativeDivide(JNIEnv *env, jobject obj,
                                                           jint a, jint b) {
    return a / b;
}

JNIEXPORT jint JNICALL Java_input_CalcManager_nativeAdd(JNIEnv *env, jobject obj,
                                                        jint a, jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL Java_input_CalcManager_nativeSubtract(JNIEnv *env, jobject obj,
                                                             jint a, jint b) {
    return a - b;
}
