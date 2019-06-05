package ru.harry.Entity;

public enum ParticipationStatus {
    Undefined(0),//Неопределенный
    Pending(1),//В ожидании
    Accepted(2),//Принято
    Rejected(3),//Отклонено
    ;

    @Override
    public String toString() {
        return name();
    }
    ParticipationStatus(int i) { }
}
