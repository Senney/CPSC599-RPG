package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Level;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.GameEntityManager;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class AStarPathfinder {
    private final PlayerManager playerManager;
    private final EnemyManager enemyManager;
    private GameEntityManager gameEntityManager;

    private class AStarNode implements Comparable<AStarNode> {
        Vector2 position;

        private int gscore, hscore, fscore;
        private AStarNode parent;

        public AStarNode(int x, int y) {
            position = new Vector2(x, y);
        }

        public void reset() {
            this.gscore = this.hscore = this.fscore = 0;
            this.parent = null;
        }

        public void computeScores(AStarNode parent, AStarNode end) {
            this.gscore = getGScore(parent);
            this.hscore = getHScore(end);
            this.fscore = this.hscore + this.gscore;
        }

        public int getGScore(AStarNode parent) {
            if (parent == null) {
                return 1;
            } else {
                return parent.getGscore() + 1;
            }
        }

        /**
         * Determines the number of squares that exist between this node and the end node.
         * @param end
         * @return The H score, or number of squares.
         */
        public int getHScore(AStarNode end) {
            return (int)(Math.abs(end.position.x - this.position.x) + Math.abs(end.position.y - this.position.y));
        }

        public int compareTo(AStarNode aStarNode) {
            if (aStarNode.position.x == this.position.x && aStarNode.position.y == this.position.y) return 0;
            return -1;
        }

        public int getGscore() {
            return gscore;
        }

        public int getHscore() {
            return hscore;
        }

        public int getFscore() {
            return fscore;
        }

        public void setParent(AStarNode parent) {
            this.parent = parent;
        }

        public AStarNode getParent() {
            return parent;
        }
    }

    private Level level;
    private AStarNode[][] levelNodes;
    private int h, w;

    public AStarPathfinder(Level level, PlayerManager playerManager, EnemyManager enemyManager, GameEntityManager gameEntityManager) {
        this.playerManager = playerManager;
        this.enemyManager = enemyManager;
        this.gameEntityManager = gameEntityManager;
        Logger.debug("Constructing AStar for level: " + level.name);
        this.level = level;
        setup();
    }

    public Level getLevel() {
        return level;
    }

    private void setup() {
        Logger.debug("Setting up values required for A-Star.");

        h = (int)level.getMapDimensions().y; w = (int)level.getMapDimensions().x;
        levelNodes = new AStarNode[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                levelNodes[y][x] = new AStarNode(x, y);
            }
        }
    }

    private void reset() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                levelNodes[y][x].reset();
            }
        }
    }

    private AStarNode getLevelNode(Vector2 position) {
        return levelNodes[(int)position.y][(int)position.x];
    }

    private boolean isMovable(int x, int y, Vector2 end) {
        return (!level.collide(x, y) && enemyManager.getEnemyAtPosition(x, y) == null &&
                (((int)end.x == x && (int)end.y == y) || playerManager.getPlayerAtPosition(x, y) == null) &&
                !gameEntityManager.checkCollision(x, y));
    }

    private void getSurroundingNodes(List<AStarNode> open, AStarNode pos, Vector2 end) {
        int xp = (int)pos.position.x, yp = (int)pos.position.y;

        // Look in the 4 cardinal directions for open nodes.
        if (isMovable(xp, yp - 1, end)) {
            open.add(levelNodes[yp-1][xp]);
        }

        if (isMovable(xp, yp + 1, end)) {
            open.add(levelNodes[yp+1][xp]);
        }

        if (isMovable(xp-1, yp, end))  {
            open.add(levelNodes[yp][xp-1]);
        }

        if (isMovable(xp+1, yp, end)) {
            open.add(levelNodes[yp][xp+1]);
        }
    }

    public List<AStarMove> getPath(Vector2 start, Vector2 end) {
        reset();

        Logger.debug("Getting path to: (" + end.x + ", " + end.y + ")");

        List<AStarNode> openNodes = new ArrayList<AStarNode>();
        List<AStarNode> closedNodes = new ArrayList<AStarNode>();
        List<AStarNode> adjacentNodes = new ArrayList<AStarNode>();

        AStarNode endNode = getLevelNode(end);

        // Set up the initial list of surrounding nodes.
        openNodes.add(getLevelNode(start));
        getLevelNode(start).computeScores(null, endNode);

        while (!openNodes.isEmpty()) {
            AStarNode current = null;
            for (AStarNode node : openNodes) {
                if (current == null || current.getFscore() > node.getFscore()) {
                    current = node;
                }
            }
            // move the selected node into the closed list.
            openNodes.remove(current);
            closedNodes.add(current);

            if (closedNodes.contains(endNode)) {
                // At this point we have found the path.
                return getPath(endNode);
            }

            adjacentNodes.clear();
            getSurroundingNodes(adjacentNodes, current, end);

            for (AStarNode node : adjacentNodes) {
                if (closedNodes.contains(node))
                    continue;

                int new_g = node.getGScore(current) + 1;
                boolean inOpen = openNodes.contains(node);
                if (!inOpen || new_g < node.getGscore()) {
                    node.setParent(current);
                    node.computeScores(current, endNode);
                    if (!inOpen) {
                        openNodes.add(node);
                    }
                }
            }
        }

        Logger.debug("No path was found to the specified end location.");
        return null;
    }

    private List<AStarMove> getPath(AStarNode endNode) {
        List<AStarMove> moves = new ArrayList<AStarMove>();
        AStarNode curNode = endNode;

        int totalIterations = 0;
        while(curNode != null) {
            AStarNode prev = curNode;
            curNode = curNode.getParent();

            if (curNode == null) {
                return moves;
            }

            AStarMove move = new AStarMove();
            Vector2 movement = new Vector2(prev.position.x - curNode.position.x, prev.position.y - curNode.position.y);
            move.x_move = (int)movement.x;
            move.y_move = (int)movement.y;
            move.position = curNode.position;
            moves.add(move);

            totalIterations++;
            if (totalIterations > 1000) {
                Logger.debug("Maxed out the back-stepping process for pathfinding.");
                return null;
            }
        }

        Logger.debug("Specified end-node did not have a path to the start node.");
        return null;
    }
}
