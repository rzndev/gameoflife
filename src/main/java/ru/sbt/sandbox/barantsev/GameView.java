package ru.sbt.sandbox.barantsev;
/**
 *
 * @author Alexander Barantsev
 *
 */
public class GameView {
    /**
     * Текущее игровое поле
     */
    private GameModel gameModel = null;

    /**
     * Инициализация игрового поля
     * @param gameModel Модель игры жизнь
     */
    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Формирование представления изменений
     * @return
     */
    public String printChanges() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE HTML>");
        sb.append("<HTML>");
        sb.append("<HEAD>");
        sb.append("<meta http-equiv=\"refresh\" content=\"1\">");
        sb.append("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        sb.append("<TITLE>Game of life</TITLE>");
        sb.append("<BODY>");
        sb.append("<TABLE>");
        int rows = gameModel.getRows();
        int cols = gameModel.getColumns();
        int r,c;
        for(r = 0; r < rows; r++) {
            sb.append("<TR>");
            for(c = 0; c < cols; c++) {
                switch (gameModel.getCellStatus(r, c)){
                    case Empty:
                        sb.append("<TD id=\"cellempty\">");
                        break;
                    case Live:
                        sb.append("<TD id=\"celllive\">");
                        break;
                    case Burn:
                        sb.append("<TD id=\"cellburn\">");
                        break;
                    case Die:
                        sb.append("<TD id=\"celldie\">");
                        break;
                }
                sb.append("</TD>");
            }
            sb.append("</TR>");
        }
        sb.append("</TABLE>");
        sb.append("</BODY>");
        sb.append("</HTML>");
        return sb.toString();
    }

    /**
     * Формирование строки для вывода
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE HTML>");
        sb.append("<HTML>");
        sb.append("<HEAD>");
        sb.append("<meta http-equiv=\"refresh\" content=\"1\">");
        sb.append("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        sb.append("<TITLE>Game of life</TITLE>");
        sb.append("<BODY>");
        sb.append("<TABLE>");
        int rows = gameModel.getRows();
        int cols = gameModel.getColumns();
        int r,c;
        for(r = 0; r < rows; r++) {
              sb.append("<TR>");
            for(c = 0; c < cols; c++) {
                if (gameModel.getCell(r, c))
                    sb.append("<TD id=\"celllive\">"); //sb.append("<img src=\"livecell.png\" alt=\"*\" border=0 height=32 width=32>");//sb.append('*');
                else
                    sb.append("<TD id=\"cellempty\">");//sb.append("<img src=\"emptycell.png\" alt=\" \" border=0 height=32 width=32>");//sb.append('*');//sb.append(' ');
                sb.append("</TD>");
            }
            sb.append("</TR>");
        }
        sb.append("</TABLE>");
        sb.append("</BODY>");
        sb.append("</HTML>");
        return sb.toString();
    }
}
