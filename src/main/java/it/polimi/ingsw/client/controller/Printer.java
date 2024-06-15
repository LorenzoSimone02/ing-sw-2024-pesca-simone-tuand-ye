package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;

/**
 * The class that represents the TUI printer
 */
public class Printer {

    /**
     * The ANSI escape code for the RESET string color
     */
    public static final String RESET = "\u001B[0m";

    /**
     * The ANSI escape code for the RED string color
     */
    public static final String RED = "\u001B[31m";

    /**
     * The ANSI escape code for the GREEN string color
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * The ANSI escape code for the YELLOW string color
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * The ANSI escape code for the BLUE string color
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * The ANSI escape code for the PURPLE string color
     */
    public static final String PURPLE = "\u001B[35m";

    /**
     * The ANSI escape code for the CYAN string color
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * The method prints the given card
     * @param card the card to print,
     *             it could be of type {@link GoldCard}, {@link StarterCard}, {@link ResourceCard}, or {@link ObjectiveCard}
     */
    public static void printCard(Card card) {
        if (card instanceof GoldCard goldCard) {
            printCard(goldCard);
            return;
        }
        if (card instanceof StarterCard starterCard) {
            printCard(starterCard);
            return;
        }
        if (card instanceof ResourceCard resourceCard) {
            printCard(resourceCard);
            return;
        }
        if (card instanceof ObjectiveCard objectiveCard) {
            printCard(objectiveCard);
        }
    }

    /**
     * The method prints the given resource card
     * @param card the resource card to print
     */
    public static void printCard(ResourceCard card) {
        String cardColor = switch (card.getBackResources().getFirst().getType()) {
            case INSECT -> PURPLE;
            case PLANT -> GREEN;
            case FUNGI -> RED;
            case ANIMAL -> CYAN;
            default -> "";
        };
        if (card.getFace() == FaceEnum.FRONT) {
            String points = card.getPoints() > 0 ? YELLOW + card.getPoints() + cardColor : " ";
            String t_l_res = "", t_r_res = "", b_l_res = "", b_r_res = "";
            for (Corner c : card.getCorners()) {
                if (c.getFace() != FaceEnum.FRONT) continue;
                switch (c.getLocation()) {
                    case TOP_LEFT:
                        if (!c.isVisible()) {
                            t_l_res = "  ";
                            break;
                        }
                        t_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + cardColor + "|";
                            case PLANT -> GREEN + "P" + cardColor + "|";
                            case FUNGI -> RED + "F" + cardColor + "|";
                            case ANIMAL -> CYAN + "A" + cardColor + "|";
                            default -> " |";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = YELLOW + c.getObject().getType().toString().charAt(0) + cardColor + "|";
                        break;
                    case TOP_RIGHT:
                        if (!c.isVisible()) {
                            t_r_res = "  ";
                            break;
                        }
                        t_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + cardColor;
                            case PLANT -> "|" + GREEN + "P" + cardColor;
                            case FUNGI -> "|" + RED + "F" + cardColor;
                            case ANIMAL -> "|" + CYAN + "A" + cardColor;
                            default -> "| ";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_r_res = "|" + YELLOW + c.getObject().getType().toString().charAt(0) + cardColor;
                        break;
                    case BOTTOM_LEFT:
                        if (!c.isVisible()) {
                            b_l_res = "__";
                            break;
                        }
                        b_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + cardColor + "|";
                            case PLANT -> GREEN + "P" + cardColor + "|";
                            case FUNGI -> RED + "F" + cardColor + "|";
                            case ANIMAL -> CYAN + "A" + cardColor + "|";
                            default -> "_|";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            b_l_res = YELLOW + c.getObject().getType().toString().charAt(0) + cardColor + "|";
                        break;
                    case BOTTOM_RIGHT:
                        if (!c.isVisible()) {
                            b_r_res = "__";
                            break;
                        }
                        b_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + cardColor;
                            case PLANT -> "|" + GREEN + "P" + cardColor;
                            case FUNGI -> "|" + RED + "F" + cardColor;
                            case ANIMAL -> "|" + CYAN + "A" + cardColor;
                            default -> "|_";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            b_r_res = "|" + YELLOW + c.getObject().getType().toString().charAt(0) + cardColor;
                        break;
                }

            }
            System.out.println(cardColor + "_______________\n" +
                    "|" + t_l_res + "    " + points + "    " + t_r_res + "|\n" +
                    "|             |\n" +
                    "|             |\n" +
                    "|" + b_l_res + "_________" + b_r_res + "|" + RESET);
        } else {
            String res = switch (card.getBackResources().getFirst().getType()) {
                case INSECT -> PURPLE + "I" + cardColor;
                case PLANT -> GREEN + "P" + cardColor;
                case FUNGI -> RED + "F" + cardColor;
                case ANIMAL -> CYAN + "A" + cardColor;
                default -> "";
            };
            System.out.println(cardColor + " ______________\n" +
                    "| |         | |\n" +
                    "|      " + res + "      |\n" +
                    "|             |\n" +
                    "|_|_________|_|" + RESET);
        }
    }

    /**
     * The method prints the given gold card
     * @param card the gold card to print
     */
    public static void printCard(GoldCard card) {
        String cardColor = switch (card.getBackResources().getFirst().getType()) {
            case INSECT -> PURPLE;
            case PLANT -> GREEN;
            case FUNGI -> RED;
            case ANIMAL -> CYAN;
            default -> "";
        };
        if (card.getFace() == FaceEnum.FRONT) {
            String points = card.getPoints() > 0 ? YELLOW + card.getPoints() + cardColor : " ";
            String t_l_res = "", t_r_res = "", b_l_res = "", b_r_res = "";
            for (Corner c : card.getCorners()) {
                if (c.getFace() != FaceEnum.FRONT) continue;
                switch (c.getLocation()) {
                    case TOP_LEFT:
                        if (!c.isVisible()) {
                            t_l_res = "  ";
                            break;
                        }
                        t_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + cardColor + "|";
                            case PLANT -> GREEN + "P" + cardColor + "|";
                            case FUNGI -> RED + "F" + cardColor + "|";
                            case ANIMAL -> CYAN + "A" + cardColor + "|";
                            default -> " |";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = YELLOW + c.getObject().getType().toString().charAt(0) + cardColor + "|";
                        break;
                    case TOP_RIGHT:
                        if (!c.isVisible()) {
                            t_r_res = "  ";
                            break;
                        }
                        t_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + cardColor;
                            case PLANT -> "|" + GREEN + "P" + cardColor;
                            case FUNGI -> "|" + RED + "F" + cardColor;
                            case ANIMAL -> "|" + CYAN + "A" + cardColor;
                            default -> "| ";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_r_res = "|" + YELLOW + c.getObject().getType().toString().charAt(0) + cardColor;
                        break;
                    case BOTTOM_LEFT:
                        if (!c.isVisible()) {
                            b_l_res = "__";
                            break;
                        }
                        b_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + cardColor + "|";
                            case PLANT -> GREEN + "P" + cardColor + "|";
                            case FUNGI -> RED + "F" + cardColor + "|";
                            case ANIMAL -> CYAN + "A" + cardColor + "|";
                            default -> "_|";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            b_l_res = YELLOW + c.getObject().getType().toString().charAt(0) + cardColor + "|";
                        break;
                    case BOTTOM_RIGHT:
                        if (!c.isVisible()) {
                            b_r_res = "__";
                            break;
                        }
                        b_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + cardColor;
                            case PLANT -> "|" + GREEN + "P" + cardColor;
                            case FUNGI -> "|" + RED + "F" + cardColor;
                            case ANIMAL -> "|" + CYAN + "A" + cardColor;
                            default -> "|_";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            b_r_res = "|" + YELLOW + c.getObject().getType().toString().charAt(0) + cardColor;
                        break;
                }

            }
            StringBuilder resNeeded = new StringBuilder();
            resNeeded.append("_".repeat(Math.max(0, 5 - card.getResourcesNeeded().size())));
            for (Resource res : card.getResourcesNeeded()) {
                resNeeded.append(switch (res.getType()) {
                    case INSECT -> PURPLE + "I" + RESET + ",";
                    case PLANT -> GREEN + "P" + RESET + ",";
                    case FUNGI -> RED + "F" + RESET + ",";
                    case ANIMAL -> CYAN + "A" + RESET + ",";
                    default -> "";
                });
            }
            resNeeded.deleteCharAt(resNeeded.length() - 1);
            resNeeded.append(cardColor);
            resNeeded.append("_".repeat(Math.max(0, 5 - card.getResourcesNeeded().size())));
            System.out.println(cardColor + "_______________\n" +
                    "|" + t_l_res + "    " + points + "    " + t_r_res + "|\n" +
                    "|             |\n" +
                    "|             |\n" +
                    "|" + b_l_res + resNeeded + b_r_res + "|" + RESET);
            System.out.println(card.getObjectiveDescription());
        } else {
            String res = switch (card.getBackResources().getFirst().getType()) {
                case INSECT -> PURPLE + "I";
                case PLANT -> GREEN + "P";
                case FUNGI -> RED + "F";
                case ANIMAL -> CYAN + "A";
                default -> "";
            };
            System.out.println(cardColor + "______________\n" +
                    "| |         | |\n" +
                    "|      " + res + "      |\n" +
                    "|             |\n" +
                    "|_|_________|_|" + RESET);
        }
    }

    /**
     * The method prints the given objective card
     * @param card the objective card to print
     */
    public static void printCard(ObjectiveCard card) {
        String cardStr = """
                _______________
                | |    %s    | |
                |             |
                |     OBJ     |
                |_|_________|_|""";
        cardStr = String.format(cardStr, YELLOW + card.getObjectiveType().getStrategy().getPointsPerPattern() + RESET);
        System.out.println(cardStr);
        System.out.println(card.getObjectiveDescription());
    }

    /**
     * The method prints the given starter card
     * @param card the starter card to print
     */
    public static void printCard(StarterCard card) {
        if (card.getFace() == FaceEnum.FRONT) {
            String t_l_res = "", t_r_res = "", b_l_res = "", b_r_res = "";
            for (Corner c : card.getCorners()) {
                if (c.getFace() != FaceEnum.FRONT) continue;
                switch (c.getLocation()) {
                    case TOP_LEFT:
                        if (!c.isVisible()) {
                            t_l_res = "  ";
                            break;
                        }
                        t_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + RESET + "|";
                            case PLANT -> GREEN + "P" + RESET + "|";
                            case FUNGI -> RED + "F" + RESET + "|";
                            case ANIMAL -> CYAN + "A" + RESET + "|";
                            default -> " |";
                        };
                        break;
                    case TOP_RIGHT:
                        if (!c.isVisible()) {
                            t_r_res = "  ";
                            break;
                        }
                        t_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + RESET;
                            case PLANT -> "|" + GREEN + "P" + RESET;
                            case FUNGI -> "|" + RED + "F" + RESET;
                            case ANIMAL -> "|" + CYAN + "A" + RESET;
                            default -> "| ";
                        };
                        break;
                    case BOTTOM_LEFT:
                        if (!c.isVisible()) {
                            b_l_res = "__";
                            break;
                        }
                        b_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + RESET + "|";
                            case PLANT -> GREEN + "P" + RESET + "|";
                            case FUNGI -> RED + "F" + RESET + "|";
                            case ANIMAL -> CYAN + "A" + RESET + "|";
                            default -> "_|";
                        };
                        break;
                    case BOTTOM_RIGHT:
                        if (!c.isVisible()) {
                            b_r_res = "__";
                            break;
                        }
                        b_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + RESET;
                            case PLANT -> "|" + GREEN + "P" + RESET;
                            case FUNGI -> "|" + RED + "F" + RESET;
                            case ANIMAL -> "|" + CYAN + "A" + RESET;
                            default -> "|_";
                        };
                        break;
                }
            }
            System.out.println(" ______________\n" +
                    "|" + t_l_res + "         " + t_r_res + "|\n" +
                    "|             |\n" +
                    "|             |\n" +
                    "|" + b_l_res + "_________" + b_r_res + "|");
        } else {
            String t_l_res = "", t_r_res = "", b_l_res = "", b_r_res = "";
            for (Corner c : card.getCorners()) {
                if (c.getFace() != FaceEnum.BACK) continue;
                switch (c.getLocation()) {
                    case TOP_LEFT:
                        if (!c.isVisible()) {
                            t_l_res = "  ";
                            break;
                        }
                        t_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + RESET + "|";
                            case PLANT -> GREEN + "P" + RESET + "|";
                            case FUNGI -> RED + "F" + RESET + "|";
                            case ANIMAL -> CYAN + "A" + RESET + "|";
                            default -> " |";
                        };
                        break;
                    case TOP_RIGHT:
                        if (!c.isVisible()) {
                            t_r_res = "  ";
                            break;
                        }
                        t_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + RESET;
                            case PLANT -> "|" + GREEN + "P" + RESET;
                            case FUNGI -> "|" + RED + "F" + RESET;
                            case ANIMAL -> "|" + CYAN + "A" + RESET;
                            default -> "| ";
                        };
                        break;
                    case BOTTOM_LEFT:
                        if (!c.isVisible()) {
                            b_l_res = "__";
                            break;
                        }
                        b_l_res = switch (c.getResource().getType()) {
                            case INSECT -> PURPLE + "I" + RESET + "|";
                            case PLANT -> GREEN + "P" + RESET + "|";
                            case FUNGI -> RED + "F" + RESET + "|";
                            case ANIMAL -> CYAN + "A" + RESET + "|";
                            default -> "_|";
                        };
                        break;
                    case BOTTOM_RIGHT:
                        if (!c.isVisible()) {
                            b_r_res = "__";
                            break;
                        }
                        b_r_res = switch (c.getResource().getType()) {
                            case INSECT -> "|" + PURPLE + "I" + RESET;
                            case PLANT -> "|" + GREEN + "P" + RESET;
                            case FUNGI -> "|" + RED + "F" + RESET;
                            case ANIMAL -> "|" + CYAN + "A" + RESET;
                            default -> "|_";
                        };
                        break;
                }
            }
            StringBuilder backRes = new StringBuilder();
            backRes.append(" ".repeat(Math.max(0, 3 - card.getBackResources().size())));
            for (Resource res : card.getBackResources()) {
                backRes.append(switch (res.getType()) {
                    case INSECT -> PURPLE + "I" + RESET + ",";
                    case PLANT -> GREEN + "P" + RESET + ",";
                    case FUNGI -> RED + "F" + RESET + ",";
                    case ANIMAL -> CYAN + "A" + RESET + ",";
                    default -> "";
                });
            }
            backRes.deleteCharAt(backRes.length() - 1);
            backRes.append(" ".repeat(Math.max(0, 3 - card.getBackResources().size())));
            System.out.println(" ______________\n" +
                    "|" + t_l_res + "         " + t_r_res + "|\n" +
                    "|    " + backRes + "    |\n" +
                    "|             |\n" +
                    "|" + b_l_res + "_________" + b_r_res + "|");
        }
    }

    /**
     * The method prints the given placed cards of a player
     * @param cards a 2D array of the placed cards to print
     */
    public static void printCardsPlaced(ResourceCard[][] cards) {
        int minX = cards.length, maxX = 0, minY = cards.length, maxY = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                if (cards[i][j] != null) {
                    if (i < minX) minX = i;
                    if (i > maxX) maxX = i;
                    if (j < minY) minY = j;
                    if (j > maxY) maxY = j;
                }
            }
        }
        System.out.print("   ");
        for (int i = minY; i <= maxY; i++) {
           System.out.print(i + (i < 9 ? "  " : " "));
        }
        System.out.println();

        for (int i = minX; i <= maxX; i++) {
            System.out.print(i + "  ");
            for (int j = minY; j <= maxY; j++) {
                Card card = cards[i][j];
                if (card != null) {
                    System.out.print("X  ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * The method returns the color of the given token
     * @param color the token color
     * @return the color of the token
     */
    public static String getColorByToken(String color){
        return switch (color) {
            case "BLUE" -> BLUE;
            case "GREEN" -> GREEN;
            case "RED" -> RED;
            case "YELLOW" -> YELLOW;
            default -> "";
        };
    }
}
