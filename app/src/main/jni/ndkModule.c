#include <jni.h>


JNIEXPORT jint JNICALL Java_input_calculator_CalcManager_nativeMultiply(JNIEnv *env, jobject obj,
                                                                        jint a, jint b) {
    return a * b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalcManager_nativeDivide(JNIEnv *env, jobject obj,
                                                                      jint a, jint b) {
    return a / b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalcManager_nativeAdd(JNIEnv *env, jobject obj,
                                                                   jint a, jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalcManager_nativeSubtract(JNIEnv *env, jobject obj,
                                                                        jint a, jint b) {
    return a - b;
}
