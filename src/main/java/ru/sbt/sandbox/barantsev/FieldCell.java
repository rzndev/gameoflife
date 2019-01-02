package ru.sbt.sandbox.barantsev;

/**
 * Класс описывает состояние и изменение игрового поля
 */
public class FieldCell {
    public enum Status {Empty, Live, Burn, Die};
    /**
     * признак живой клетки
     */
    public boolean live;
    /**
     * статус изменения клетки (используется для хранения изменений между двумя последовательными поколениями)
     */
    public Status status;
}
