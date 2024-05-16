package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;

public class Printer {

    public static final String RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
            //TODO: Spiegazione del GoldObjective
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

    public static void printCard(ObjectiveCard card) {
        System.out.println("""
                 ______________
                | |         | |
                |     OBJ     |
                |             |
                |_|_________|_|""");
        System.out.println(card.getObjectiveDescription());
    }

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
}
