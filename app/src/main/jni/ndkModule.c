#include <jni.h>


JNIEXPORT jint JNICALL Java_input_calculator_CalculationItem_nativeMultiply(JNIEnv *env,
                                                                            jobject obj,
                                                                            jint a, jint b) {
    return a * b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalculationItem_nativeDivide(JNIEnv *env, jobject obj,
                                                                          jint a, jint b) {
    return a / b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalculationItem_nativeAdd(JNIEnv *env, jobject obj,
                                                                       jint a, jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL Java_input_calculator_CalculationItem_nativeSubtract(JNIEnv *env,
                                                                            jobject obj,
                                                                            jint a, jint b) {
    return a - b;
}
