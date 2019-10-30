/*
 * Created by Mark Abramenko on 09.08.19 15:34
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 09.08.19 15:33
 */

package com.example.wallto.base;

public interface BaseView {
    // непонятно для чего нужны эти методы? если нужна функция логгера - можно сделать класс логгер
    // и использовать его везде где нужно. Можно пойти дальше и сделать логгер с разными писателями
    // и например в тестах передавать писателя который будет писать в System.out, в дебаге в Log
    // а в продакшене в какой-нибудь крашлитикс
    void writeLog(String mes);
    void writeLog(String mes, Throwable e);
}
