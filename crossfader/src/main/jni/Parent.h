//
// Created by cuieney on 16/8/16.
//

#ifndef UMENG_PARENT_H
#define UMENG_PARENT_H

#include "ProcessBase.h"

/**
* 功能：父进程的实现
* @author wangqiang
* @date 2014-03-14
*/
class Parent : public ProcessBase
{
public:

    Parent( JNIEnv* env, jobject jobj );

    virtual bool create_child( );

    virtual void do_work();

    virtual void catch_child_dead_signal();

    virtual void on_child_end();

    virtual ~Parent();

    bool create_channel();

/**
* 获取父进程的JNIEnv
*/
    JNIEnv *get_jni_env() const;

/**
* 获取Java层的对象
*/
    jobject get_jobj() const;

private:

    JNIEnv *m_env;

    jobject m_jobj;

};
#endif //UMENG_PARENT_H
