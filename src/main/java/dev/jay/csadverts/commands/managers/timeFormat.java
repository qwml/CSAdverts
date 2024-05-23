package dev.jay.csadverts.commands.managers;

public class timeFormat {

    public String formattedTime(int seconds){

        int hours = seconds / 3600, remainder = seconds % 3600, minutes = remainder / 60, seconds1 = remainder % 60;
        String disHour = (hours < 10 ? "0" : "") + hours, disMinu = (minutes < 10 ? "0" : "") + minutes, disSec = (seconds1 < 10 ? "0" : "") + seconds1;
        String time = disHour + " hours " + disMinu + " minutes " + disSec + " seconds";

        return time;
    }
}
