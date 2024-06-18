#include <jni.h>
#include <string>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>

#define TEXTLCD_ON              1
#define TEXTLCD_OFF     2
#define TEXTLCD_INIT    3
#define TEXTLCD_CLEAR           4

#define TEXTLCD_LINE1           5
#define TEXTLCD_LINE2           6

extern "C" JNIEXPORT jstring JNICALL
Java_net_atlas_projectalpha_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_net_atlas_projectalpha_PlayActivity_setLED(JNIEnv* env, jobject, jint length) {
    char leds[8];

    for (int i = 0; i < 8; i++) {
        if (i < length) {
            leds[7 - i] = '1';
            continue;
        }

        leds[7 - i] = '0';
    }

    int file;
    unsigned char value = strtol(leds, NULL, 2);

    file = open("/dev/fpga_led", O_RDWR);

    write(file, &value, 1);

    close(file);
}

extern "C" JNIEXPORT void JNICALL Java_net_atlas_projectalpha_PlayActivity_setLCD(JNIEnv* env, jobject, jstring line1, jstring line2) {
    int file = open("/dev/fpga_textlcd", O_WRONLY);

    // Clear
    ioctl(file, TEXTLCD_CLEAR);

    usleep(100000);

    const char * line1_str = env->GetStringUTFChars(line1, JNI_FALSE);
    const char * line2_str = env->GetStringUTFChars(line2, JNI_FALSE);

    ioctl(file, TEXTLCD_LINE1);
    write(file, line1_str, strlen(line1_str));

    ioctl(file, TEXTLCD_LINE2);
    write(file, line2_str, strlen(line2_str));

    env->ReleaseStringUTFChars(line1, line1_str);
    env->ReleaseStringUTFChars(line2, line2_str);

    close(file);
}