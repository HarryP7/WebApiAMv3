package ru.harry.Entity;

public enum EventStatus {
    Create(0),//Создан
    Joined(1),//Присоединились
    Canceled(2),//Отменено
    Live(3),//Идет сейчас
    Completed(4)//Завершен
    ;

    @Override
    public String toString() {
        return name();
    }
    EventStatus(int i) { }
}
