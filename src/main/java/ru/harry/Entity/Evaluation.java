package ru.harry.Entity;

public enum Evaluation {
    Undefined(0),//Неопределенный
    Amazing(1),//Удивительно
    Good(2),//Хорошо
    Ordinary(3),//Обычно
    Bad(4),//Плохо
    Awful(5)//Ужастно
    ;

    @Override
    public String toString() {
        return name();
    }
    Evaluation(int i) { }
}
