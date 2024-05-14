package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceCard extends Card {
    int xCoord;
    int yCoord;
    final int points;
    List<Corner> corners;
    final List<Resource> backResources;

    public ResourceCard(File jsonFile) {
        this.corners = new ArrayList<>();
        this.corners.add(new Corner(CornerLocationEnum.TOP_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.TOP_RIGHT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_RIGHT, null, null, FaceEnum.BACK, true));

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            ResourceCard proprieties = gson.fromJson(reader, ResourceCard.class);
            this.id = proprieties.getId();
            this.color = proprieties.getColor();
            this.face = proprieties.getFace();
            this.points = proprieties.getPoints();
            this.backResources = proprieties.getBackResources();
            this.corners.addAll(proprieties.getCorners());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getPoints() {
        return points;
    }

    public List<Corner> getCorners() {
        return corners;
    }

    public Corner getCorner(CornerLocationEnum location) {
        return corners.stream().filter(corner -> corner.getLocation().equals(location)).findFirst().orElse(null);
    }

    public List<Resource> getBackResources() {
        return backResources;
    }

    public String toString() {
        if (this.face == FaceEnum.FRONT) {
            String points = this.points > 0 ? String.valueOf(this.points) : " ";
            String t_l_res = "", t_r_res = "", b_l_res = "", b_r_res = "";
            for (Corner c : this.corners) {
                switch (c.getLocation()) {
                    case TOP_LEFT:
                        t_l_res = switch (c.getResource().getType()) {
                            case INSECT -> Printer.ANSI_PURPLE + "I" + Printer.ANSI_RESET;
                            case PLANT -> Printer.ANSI_GREEN + "P" + Printer.ANSI_RESET;
                            case FUNGI -> Printer.ANSI_RED + "F" + Printer.ANSI_RESET;
                            case ANIMAL -> Printer.ANSI_CYAN + "A" + Printer.ANSI_RESET;
                            default -> " ";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = Printer.ANSI_YELLOW + c.getObject().getType().toString().charAt(0) + Printer.ANSI_RESET;
                        break;
                    case TOP_RIGHT:
                        t_r_res = switch (c.getResource().getType()) {
                            case INSECT -> Printer.ANSI_PURPLE + "I" + Printer.ANSI_RESET;
                            case PLANT -> Printer.ANSI_GREEN + "P" + Printer.ANSI_RESET;
                            case FUNGI -> Printer.ANSI_RED + "F" + Printer.ANSI_RESET;
                            case ANIMAL -> Printer.ANSI_CYAN + "A" + Printer.ANSI_RESET;
                            default -> " ";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = Printer.ANSI_YELLOW + c.getObject().getType().toString().charAt(0) + Printer.ANSI_RESET;
                        break;
                    case BOTTOM_LEFT:
                        b_l_res = switch (c.getResource().getType()) {
                            case INSECT -> Printer.ANSI_PURPLE + "I" + Printer.ANSI_RESET;
                            case PLANT -> Printer.ANSI_GREEN + "P" + Printer.ANSI_RESET;
                            case FUNGI -> Printer.ANSI_RED + "F" + Printer.ANSI_RESET;
                            case ANIMAL -> Printer.ANSI_CYAN + "A" + Printer.ANSI_RESET;
                            default -> "_";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = Printer.ANSI_YELLOW + c.getObject().getType().toString().charAt(0) + Printer.ANSI_RESET;
                        break;
                    case BOTTOM_RIGHT:
                        b_r_res = switch (c.getResource().getType()) {
                            case INSECT -> Printer.ANSI_PURPLE + "I" + Printer.ANSI_RESET;
                            case PLANT -> Printer.ANSI_GREEN + "P" + Printer.ANSI_RESET;
                            case FUNGI -> Printer.ANSI_RED + "F" + Printer.ANSI_RESET;
                            case ANIMAL -> Printer.ANSI_CYAN + "A" + Printer.ANSI_RESET;
                            default -> "_";
                        };
                        if (c.getObject().getType() != ObjectTypeEnum.EMPTY)
                            t_l_res = Printer.ANSI_YELLOW + c.getObject().getType().toString().charAt(0) + Printer.ANSI_RESET;
                        break;
                }

            }
            return " ______________\n" +
                    "|" + t_l_res + "|    " + points + "    |" + t_r_res + "|\n" +
                    "|             |\n" +
                    "|             |\n" +
                    "|" + b_l_res + "|_________|" + b_r_res + "|";
        } else {
            String res = switch (this.backResources.getFirst().getType()) {
                case INSECT -> Printer.ANSI_PURPLE + "I" + Printer.ANSI_RESET;
                case PLANT -> Printer.ANSI_GREEN + "P" + Printer.ANSI_RESET;
                case FUNGI -> Printer.ANSI_RED + "F" + Printer.ANSI_RESET;
                case ANIMAL -> Printer.ANSI_CYAN + "A" + Printer.ANSI_RESET;
                default -> "";
            };
            return " ______________\n" +
                    "|_|         |_|\n" +
                    "|      " + res + "      |\n" +
                    "|_           _|\n" +
                    "|_|_________|_|";
        }
    }
}
