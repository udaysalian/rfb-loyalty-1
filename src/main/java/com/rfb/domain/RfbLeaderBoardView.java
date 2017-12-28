package com.rfb.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.text.DecimalFormat;

public class RfbLeaderBoardView implements Comparable<RfbLeaderBoardView> {

    private final Long userId;
    private final String name;
    private Long distance;
    private String percent;

    /**
     * Create a record with the first distance of 5Km
     * @param userId - primary key value from the JHI_USER table
     * @param name - combination of the first and last name from the JHI_USER table
     * @param distance - the max distance a event counts for
     */
    public RfbLeaderBoardView(Long userId, String name, long distance) {
        this.userId = userId;
        this.name = name;
        this.distance = distance;
    }

    /**
     * Increase the distance the runner has logged by 5Km
     */
    public void incrementDistance(long increment) {
        distance += increment;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getDistance() {
        return distance;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(long leaderDistance, DecimalFormat percentFormat) {
        double percentDistance = (double) distance / (double) leaderDistance;
        percent = percentFormat.format(percentDistance);
    }

    public RfbLeaderBoardView percent(String percent) {
        this.percent = percent;
        return this;
    }

    @Override
    public int compareTo(RfbLeaderBoardView that) {
        return ComparisonChain.start()
                .compare(this.distance, that.distance)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RfbLeaderBoardView that = (RfbLeaderBoardView) o;

        return Objects.equal(this.userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("name", name)
                .add("distance", distance)
                .add("percent", percent)
                .toString();
    }
}
