package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final int boardSize = 3;

    private static State state = new State(boardSize);

    public void action(Map<String, Object> view) {
        view.put("state", state);
    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) throws NotFoundException {
        Optional<String> strings = request.getParameterMap().keySet().stream()
                .filter(x -> x.matches("cell_\\d{2}")).findFirst();
        String str;
        if (!strings.isPresent()) {
            throw new NotFoundException();
        } else {
            str = strings.get();
        }
        str = str.substring(str.length() - 2);
        if (state.getPhase() == Phase.RUNNING) {
            state.makeMove(Integer.parseInt(String.valueOf(str.charAt(0))), Integer.parseInt(String.valueOf(str.charAt(1))));
        }
        view.put("state", state);
    }

    public void newGame(Map<String, Object> view) {
        state = new State(boardSize);
        view.put("state", state);
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }

    public enum Cell {
        X, O
    }

    public static class State {
        private final int size;
        private int empty; 
        private final Cell[][] cells;
        private Phase phase;
        private boolean crossesMove;

        public State(int size) {
            this.size = size;
            empty = size * size;
            cells = new Cell[size][size];
            phase = Phase.RUNNING;
            crossesMove = true;
        }

        public void setCrossesMove(boolean crossesMove) {
            this.crossesMove = crossesMove;
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        public int getSize() {
            return size;
        }

        public Cell getCell(int row, int column) {
            return cells[row][column];
        }

        public Phase getPhase() {
            return phase;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public void setPhase(Phase phase) {
            this.phase = phase;
        }

        public void makeMove(int row, int column) {
            Cell player = crossesMove ? Cell.X : Cell.O;
            if (cells[row][column] == null) {
                cells[row][column] = player;
                empty--;
                if (checker(player, size, row, column)) {
                    phase = crossesMove ? Phase.WON_X : Phase.WON_O;
                } else if (empty == 0) {
                    phase = Phase.DRAW;
                }
                crossesMove = !crossesMove;
            }
        }

        private boolean checker(Cell move, int k, int row, int column) {
            return counter(move, -1, 0, row, column) + counter(move, 1, 0, row, column) > k ||
                    counter(move, 0, -1, row, column) + counter(move, 0, 1, row, column) > k ||
                    counter(move, -1, -1, row, column) + counter(move, 1, 1, row, column) > k ||
                    counter(move, 1, -1, row, column) + counter(move, -1, 1, row, column) > k;
        }

        private int counter(Cell move, int dr, int dc, int row, int column) {
            int cnt = 0;
            for (int x = row, y = column; 0 <= x && x < size && 0 <= y &&
                    y < size && move == getCell(x, y); x += dr, y += dc) {
                cnt++;
            }
            return cnt;
        }
    }
}
