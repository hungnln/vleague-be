package com.hungnln.vleague.constant.activity;

public enum ActivityType {
    StartFirstHalf,
    Goal,
    OwnGoal,
    RedCard,
    YellowCard,
    Foul,
    Offside,
    KickOff,
    Penalty,
    Corner,
    ThrowIn,
    Header,
    BackHeel,
    EndFirstHalf,
    StartSecondHalf,
    ExtraTime,
    EndSecondHalf,
    EndMatch;
    public static ActivityType lookup(int input){
        for (ActivityType type: values()){
            if (type.ordinal() == input) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid input: " + input);
    }
}
