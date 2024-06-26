
package org.acme.conferencescheduling.domain;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfiguration;
import ai.timefold.solver.core.api.domain.constraintweight.ConstraintWeight;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

@ConstraintConfiguration(constraintPackage = "ai.timefold.solver.examples.conferencescheduling.score")
public class ConferenceConstraintConfiguration {

    public static final String ROOM_UNAVAILABLE_TIMESLOT = "Room unavailable timeslot";
    public static final String ROOM_CONFLICT = "Room conflict";
    public static final String SPEAKER_UNAVAILABLE_TIMESLOT = "Speaker unavailable timeslot";
    public static final String SPEAKER_CONFLICT = "Speaker conflict";
    public static final String TALK_PREREQUISITE_TALKS = "Talk prerequisite talks";
    public static final String TALK_MUTUALLY_EXCLUSIVE_TALKS_TAGS = "Talk mutually-exclusive-talks tags";
    public static final String CONSECUTIVE_TALKS_PAUSE = "Consecutive talks pause";
    public static final String CROWD_CONTROL = "Crowd control";

    public static final String SPEAKER_REQUIRED_TIMESLOT_TAGS = "Speaker required timeslot tags";
    public static final String SPEAKER_PROHIBITED_TIMESLOT_TAGS = "Speaker prohibited timeslot tags";
    public static final String TALK_REQUIRED_TIMESLOT_TAGS = "Talk required timeslot tags";
    public static final String TALK_PROHIBITED_TIMESLOT_TAGS = "Talk prohibited timeslot tags";
    public static final String SPEAKER_REQUIRED_ROOM_TAGS = "Speaker required room tags";
    public static final String SPEAKER_PROHIBITED_ROOM_TAGS = "Speaker prohibited room tags";
    public static final String TALK_REQUIRED_ROOM_TAGS = "Talk required room tags";
    public static final String TALK_PROHIBITED_ROOM_TAGS = "Talk prohibited room tags";

    public static final String THEME_TRACK_CONFLICT = "Theme track conflict";
    public static final String THEME_TRACK_ROOM_STABILITY = "Theme track room stability";
    public static final String SECTOR_CONFLICT = "Sector conflict";
    public static final String AUDIENCE_TYPE_DIVERSITY = "Audience type diversity";
    public static final String AUDIENCE_TYPE_THEME_TRACK_CONFLICT = "Audience type theme track conflict";
    public static final String AUDIENCE_LEVEL_DIVERSITY = "Audience level diversity";
    public static final String CONTENT_AUDIENCE_LEVEL_FLOW_VIOLATION = "Content audience level flow violation";
    public static final String CONTENT_CONFLICT = "Content conflict";
    public static final String LANGUAGE_DIVERSITY = "Language diversity";
    public static final String SAME_DAY_TALKS = "Same day talks";
    public static final String POPULAR_TALKS = "Popular talks";

    public static final String SPEAKER_PREFERRED_TIMESLOT_TAGS = "Speaker preferred timeslot tags";
    public static final String SPEAKER_UNDESIRED_TIMESLOT_TAGS = "Speaker undesired timeslot tags";
    public static final String TALK_PREFERRED_TIMESLOT_TAGS = "Talk preferred timeslot tags";
    public static final String TALK_UNDESIRED_TIMESLOT_TAGS = "Talk undesired timeslot tags";
    public static final String SPEAKER_PREFERRED_ROOM_TAGS = "Speaker preferred room tags";
    public static final String SPEAKER_UNDESIRED_ROOM_TAGS = "Speaker undesired room tags";
    public static final String TALK_PREFERRED_ROOM_TAGS = "Talk preferred room tags";
    public static final String TALK_UNDESIRED_ROOM_TAGS = "Talk undesired room tags";
    public static final String SPEAKER_MAKESPAN = "Speaker makespan";

    private int minimumConsecutiveTalksPauseInMinutes = 30;

    @ConstraintWeight(ROOM_UNAVAILABLE_TIMESLOT)
    private HardSoftScore roomUnavailableTimeslot = HardSoftScore.ofHard(100_000);
    @ConstraintWeight(ROOM_CONFLICT)
    private HardSoftScore roomConflict = HardSoftScore.ofHard(1_000);
    @ConstraintWeight(SPEAKER_UNAVAILABLE_TIMESLOT)
    private HardSoftScore speakerUnavailableTimeslot = HardSoftScore.ofHard(100);
    @ConstraintWeight(SPEAKER_CONFLICT)
    private HardSoftScore speakerConflict = HardSoftScore.ofHard(10);
    @ConstraintWeight(TALK_PREREQUISITE_TALKS)
    private HardSoftScore talkPrerequisiteTalks = HardSoftScore.ofHard(10);
    @ConstraintWeight(TALK_MUTUALLY_EXCLUSIVE_TALKS_TAGS)
    private HardSoftScore talkMutuallyExclusiveTalksTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(CONSECUTIVE_TALKS_PAUSE)
    private HardSoftScore consecutiveTalksPause = HardSoftScore.ofHard(1);
    @ConstraintWeight(CROWD_CONTROL)
    private HardSoftScore crowdControl = HardSoftScore.ofHard(1);

    @ConstraintWeight(SPEAKER_REQUIRED_TIMESLOT_TAGS)
    private HardSoftScore speakerRequiredTimeslotTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(SPEAKER_PROHIBITED_TIMESLOT_TAGS)
    private HardSoftScore speakerProhibitedTimeslotTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(TALK_REQUIRED_TIMESLOT_TAGS)
    private HardSoftScore talkRequiredTimeslotTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(TALK_PROHIBITED_TIMESLOT_TAGS)
    private HardSoftScore talkProhibitedTimeslotTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(SPEAKER_REQUIRED_ROOM_TAGS)
    private HardSoftScore speakerRequiredRoomTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(SPEAKER_PROHIBITED_ROOM_TAGS)
    private HardSoftScore speakerProhibitedRoomTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(TALK_REQUIRED_ROOM_TAGS)
    private HardSoftScore talkRequiredRoomTags = HardSoftScore.ofHard(1);
    @ConstraintWeight(TALK_PROHIBITED_ROOM_TAGS)
    private HardSoftScore talkProhibitedRoomTags = HardSoftScore.ofHard(1);

    @ConstraintWeight(THEME_TRACK_CONFLICT)
    private HardSoftScore themeTrackConflict = HardSoftScore.ofSoft(10);
    @ConstraintWeight(THEME_TRACK_ROOM_STABILITY)
    private HardSoftScore themeTrackRoomStability = HardSoftScore.ofSoft(10);
    @ConstraintWeight(SECTOR_CONFLICT)
    private HardSoftScore sectorConflict = HardSoftScore.ofSoft(10);
    @ConstraintWeight(AUDIENCE_TYPE_DIVERSITY)
    private HardSoftScore audienceTypeDiversity = HardSoftScore.ofSoft(1);
    @ConstraintWeight(AUDIENCE_TYPE_THEME_TRACK_CONFLICT)
    private HardSoftScore audienceTypeThemeTrackConflict = HardSoftScore.ofSoft(1);
    @ConstraintWeight(AUDIENCE_LEVEL_DIVERSITY)
    private HardSoftScore audienceLevelDiversity = HardSoftScore.ofSoft(1);
    @ConstraintWeight(CONTENT_AUDIENCE_LEVEL_FLOW_VIOLATION)
    private HardSoftScore contentAudienceLevelFlowViolation = HardSoftScore.ofSoft(10);
    @ConstraintWeight(CONTENT_CONFLICT)
    private HardSoftScore contentConflict = HardSoftScore.ofSoft(100);
    @ConstraintWeight(LANGUAGE_DIVERSITY)
    private HardSoftScore languageDiversity = HardSoftScore.ofSoft(10);
    @ConstraintWeight(SAME_DAY_TALKS)
    private HardSoftScore sameDayTalks = HardSoftScore.ofSoft(10);
    @ConstraintWeight(POPULAR_TALKS)
    private HardSoftScore popularTalks = HardSoftScore.ofSoft(10);

    @ConstraintWeight(SPEAKER_PREFERRED_TIMESLOT_TAGS)
    private HardSoftScore speakerPreferredTimeslotTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(SPEAKER_UNDESIRED_TIMESLOT_TAGS)
    private HardSoftScore speakerUndesiredTimeslotTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(TALK_PREFERRED_TIMESLOT_TAGS)
    private HardSoftScore talkPreferredTimeslotTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(TALK_UNDESIRED_TIMESLOT_TAGS)
    private HardSoftScore talkUndesiredTimeslotTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(SPEAKER_PREFERRED_ROOM_TAGS)
    private HardSoftScore speakerPreferredRoomTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(SPEAKER_UNDESIRED_ROOM_TAGS)
    private HardSoftScore speakerUndesiredRoomTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(TALK_PREFERRED_ROOM_TAGS)
    private HardSoftScore talkPreferredRoomTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(TALK_UNDESIRED_ROOM_TAGS)
    private HardSoftScore talkUndesiredRoomTags = HardSoftScore.ofSoft(20);
    @ConstraintWeight(SPEAKER_MAKESPAN)
    private HardSoftScore speakerMakespan = HardSoftScore.ofSoft(20);

    public ConferenceConstraintConfiguration() {
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public int getMinimumConsecutiveTalksPauseInMinutes() {
        return minimumConsecutiveTalksPauseInMinutes;
    }

    public void setMinimumConsecutiveTalksPauseInMinutes(int minimumConsecutiveTalksPauseInMinutes) {
        this.minimumConsecutiveTalksPauseInMinutes = minimumConsecutiveTalksPauseInMinutes;
    }

    public HardSoftScore getRoomUnavailableTimeslot() {
        return roomUnavailableTimeslot;
    }

    public void setRoomUnavailableTimeslot(HardSoftScore roomUnavailableTimeslot) {
        this.roomUnavailableTimeslot = roomUnavailableTimeslot;
    }

    public HardSoftScore getRoomConflict() {
        return roomConflict;
    }

    public void setRoomConflict(HardSoftScore roomConflict) {
        this.roomConflict = roomConflict;
    }

    public HardSoftScore getSpeakerUnavailableTimeslot() {
        return speakerUnavailableTimeslot;
    }

    public void setSpeakerUnavailableTimeslot(HardSoftScore speakerUnavailableTimeslot) {
        this.speakerUnavailableTimeslot = speakerUnavailableTimeslot;
    }

    public HardSoftScore getSpeakerConflict() {
        return speakerConflict;
    }

    public void setSpeakerConflict(HardSoftScore speakerConflict) {
        this.speakerConflict = speakerConflict;
    }

    public HardSoftScore getTalkPrerequisiteTalks() {
        return talkPrerequisiteTalks;
    }

    public void setTalkPrerequisiteTalks(HardSoftScore talkPrerequisiteTalks) {
        this.talkPrerequisiteTalks = talkPrerequisiteTalks;
    }

    public HardSoftScore getTalkMutuallyExclusiveTalksTags() {
        return talkMutuallyExclusiveTalksTags;
    }

    public void setTalkMutuallyExclusiveTalksTags(HardSoftScore talkMutuallyExclusiveTalksTags) {
        this.talkMutuallyExclusiveTalksTags = talkMutuallyExclusiveTalksTags;
    }

    public HardSoftScore getConsecutiveTalksPause() {
        return consecutiveTalksPause;
    }

    public void setConsecutiveTalksPause(HardSoftScore consecutiveTalksPause) {
        this.consecutiveTalksPause = consecutiveTalksPause;
    }

    public HardSoftScore getCrowdControl() {
        return crowdControl;
    }

    public void setCrowdControl(HardSoftScore crowdControl) {
        this.crowdControl = crowdControl;
    }

    public HardSoftScore getSpeakerRequiredTimeslotTags() {
        return speakerRequiredTimeslotTags;
    }

    public void setSpeakerRequiredTimeslotTags(HardSoftScore speakerRequiredTimeslotTags) {
        this.speakerRequiredTimeslotTags = speakerRequiredTimeslotTags;
    }

    public HardSoftScore getSpeakerProhibitedTimeslotTags() {
        return speakerProhibitedTimeslotTags;
    }

    public void setSpeakerProhibitedTimeslotTags(HardSoftScore speakerProhibitedTimeslotTags) {
        this.speakerProhibitedTimeslotTags = speakerProhibitedTimeslotTags;
    }

    public HardSoftScore getTalkRequiredTimeslotTags() {
        return talkRequiredTimeslotTags;
    }

    public void setTalkRequiredTimeslotTags(HardSoftScore talkRequiredTimeslotTags) {
        this.talkRequiredTimeslotTags = talkRequiredTimeslotTags;
    }

    public HardSoftScore getTalkProhibitedTimeslotTags() {
        return talkProhibitedTimeslotTags;
    }

    public void setTalkProhibitedTimeslotTags(HardSoftScore talkProhibitedTimeslotTags) {
        this.talkProhibitedTimeslotTags = talkProhibitedTimeslotTags;
    }

    public HardSoftScore getSpeakerRequiredRoomTags() {
        return speakerRequiredRoomTags;
    }

    public void setSpeakerRequiredRoomTags(HardSoftScore speakerRequiredRoomTags) {
        this.speakerRequiredRoomTags = speakerRequiredRoomTags;
    }

    public HardSoftScore getSpeakerProhibitedRoomTags() {
        return speakerProhibitedRoomTags;
    }

    public void setSpeakerProhibitedRoomTags(HardSoftScore speakerProhibitedRoomTags) {
        this.speakerProhibitedRoomTags = speakerProhibitedRoomTags;
    }

    public HardSoftScore getTalkRequiredRoomTags() {
        return talkRequiredRoomTags;
    }

    public void setTalkRequiredRoomTags(HardSoftScore talkRequiredRoomTags) {
        this.talkRequiredRoomTags = talkRequiredRoomTags;
    }

    public HardSoftScore getTalkProhibitedRoomTags() {
        return talkProhibitedRoomTags;
    }

    public void setTalkProhibitedRoomTags(HardSoftScore talkProhibitedRoomTags) {
        this.talkProhibitedRoomTags = talkProhibitedRoomTags;
    }

    public HardSoftScore getThemeTrackConflict() {
        return themeTrackConflict;
    }

    public void setThemeTrackConflict(HardSoftScore themeTrackConflict) {
        this.themeTrackConflict = themeTrackConflict;
    }

    public HardSoftScore getThemeTrackRoomStability() {
        return themeTrackRoomStability;
    }

    public void setThemeTrackRoomStability(HardSoftScore themeTrackRoomStability) {
        this.themeTrackRoomStability = themeTrackRoomStability;
    }

    public HardSoftScore getSectorConflict() {
        return sectorConflict;
    }

    public void setSectorConflict(HardSoftScore sectorConflict) {
        this.sectorConflict = sectorConflict;
    }

    public HardSoftScore getAudienceTypeDiversity() {
        return audienceTypeDiversity;
    }

    public void setAudienceTypeDiversity(HardSoftScore audienceTypeDiversity) {
        this.audienceTypeDiversity = audienceTypeDiversity;
    }

    public HardSoftScore getAudienceTypeThemeTrackConflict() {
        return audienceTypeThemeTrackConflict;
    }

    public void setAudienceTypeThemeTrackConflict(HardSoftScore audienceTypeThemeTrackConflict) {
        this.audienceTypeThemeTrackConflict = audienceTypeThemeTrackConflict;
    }

    public HardSoftScore getAudienceLevelDiversity() {
        return audienceLevelDiversity;
    }

    public void setAudienceLevelDiversity(HardSoftScore audienceLevelDiversity) {
        this.audienceLevelDiversity = audienceLevelDiversity;
    }

    public HardSoftScore getContentAudienceLevelFlowViolation() {
        return contentAudienceLevelFlowViolation;
    }

    public void setContentAudienceLevelFlowViolation(HardSoftScore contentAudienceLevelFlowViolation) {
        this.contentAudienceLevelFlowViolation = contentAudienceLevelFlowViolation;
    }

    public HardSoftScore getContentConflict() {
        return contentConflict;
    }

    public void setContentConflict(HardSoftScore contentConflict) {
        this.contentConflict = contentConflict;
    }

    public HardSoftScore getLanguageDiversity() {
        return languageDiversity;
    }

    public void setLanguageDiversity(HardSoftScore languageDiversity) {
        this.languageDiversity = languageDiversity;
    }

    public HardSoftScore getSameDayTalks() {
        return sameDayTalks;
    }

    public void setSameDayTalks(HardSoftScore sameDayTalks) {
        this.sameDayTalks = sameDayTalks;
    }

    public HardSoftScore getPopularTalks() {
        return popularTalks;
    }

    public void setPopularTalks(HardSoftScore popularTalks) {
        this.popularTalks = popularTalks;
    }

    public HardSoftScore getSpeakerPreferredTimeslotTags() {
        return speakerPreferredTimeslotTags;
    }

    public void setSpeakerPreferredTimeslotTags(HardSoftScore speakerPreferredTimeslotTags) {
        this.speakerPreferredTimeslotTags = speakerPreferredTimeslotTags;
    }

    public HardSoftScore getSpeakerUndesiredTimeslotTags() {
        return speakerUndesiredTimeslotTags;
    }

    public void setSpeakerUndesiredTimeslotTags(HardSoftScore speakerUndesiredTimeslotTags) {
        this.speakerUndesiredTimeslotTags = speakerUndesiredTimeslotTags;
    }

    public HardSoftScore getTalkPreferredTimeslotTags() {
        return talkPreferredTimeslotTags;
    }

    public void setTalkPreferredTimeslotTags(HardSoftScore talkPreferredTimeslotTags) {
        this.talkPreferredTimeslotTags = talkPreferredTimeslotTags;
    }

    public HardSoftScore getTalkUndesiredTimeslotTags() {
        return talkUndesiredTimeslotTags;
    }

    public void setTalkUndesiredTimeslotTags(HardSoftScore talkUndesiredTimeslotTags) {
        this.talkUndesiredTimeslotTags = talkUndesiredTimeslotTags;
    }

    public HardSoftScore getSpeakerPreferredRoomTags() {
        return speakerPreferredRoomTags;
    }

    public void setSpeakerPreferredRoomTags(HardSoftScore speakerPreferredRoomTags) {
        this.speakerPreferredRoomTags = speakerPreferredRoomTags;
    }

    public HardSoftScore getSpeakerUndesiredRoomTags() {
        return speakerUndesiredRoomTags;
    }

    public void setSpeakerUndesiredRoomTags(HardSoftScore speakerUndesiredRoomTags) {
        this.speakerUndesiredRoomTags = speakerUndesiredRoomTags;
    }

    public HardSoftScore getTalkPreferredRoomTags() {
        return talkPreferredRoomTags;
    }

    public void setTalkPreferredRoomTags(HardSoftScore talkPreferredRoomTags) {
        this.talkPreferredRoomTags = talkPreferredRoomTags;
    }

    public HardSoftScore getTalkUndesiredRoomTags() {
        return talkUndesiredRoomTags;
    }

    public void setTalkUndesiredRoomTags(HardSoftScore talkUndesiredRoomTags) {
        this.talkUndesiredRoomTags = talkUndesiredRoomTags;
    }

    public HardSoftScore getSpeakerMakespan() {
        return speakerMakespan;
    }

    public void setSpeakerMakespan(HardSoftScore speakerMakespan) {
        this.speakerMakespan = speakerMakespan;
    }
}
