/**
 * Created by Binh Vu (github: yobavu) on 3/27/17.
 */

package com.yobavu.jtwitch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Twitch user's subscription.
 */
public class UserSubscription {
    @SerializedName("_id")
    private String id;

    @SerializedName("sub_plan")
    private String subPlan;

    @SerializedName("sub_plan_name")
    private String subPlanName;

    @SerializedName("channel")
    private Channel channel;

    @SerializedName("created_at")
    private String createdAt;

    /**
     * Gets subscription id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the sub plan.
     */
    public String getSubPlan() {
        return subPlan;
    }

    /**
     * Gets the sub plan name.
     */
    public String getSubPlanName() {
        return subPlanName;
    }

    /**
     * Gets subscribed channel information.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Gets date subscription was created on.
     */
    public String getCreatedAt() {
        return createdAt;
    }
}
