package org.osforce.connect.task.team;

import java.util.Map;

import org.osforce.connect.entity.stream.Activity;
import org.osforce.connect.entity.team.TeamMember;
import org.osforce.connect.service.stream.ActivityService;
import org.osforce.connect.service.team.MemberService;
import org.osforce.spring4me.task.AbstractTask;
import org.osforce.spring4me.task.annotation.Task;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gavin
 * @since 1.0.0
 * @create Mar 3, 2011 - 9:39:41 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Task
public class MemberActivityStreamTask extends AbstractTask {

	private ActivityService activityService;
	private MemberService memberService;

	public MemberActivityStreamTask() {
	}

	@Autowired
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	protected void doTask(Map<Object, Object> context) throws Exception {
		Long memberId = (Long) context.get("memberId");
		TeamMember member = memberService.getMember(memberId);
		String template = (String) context.get("template");
		Activity activity = new Activity();
		activity.setLinkedId(memberId);
		activity.setEntity(TeamMember.NAME);
		activity.setType(TeamMember.NAME);
		activity.setDescription(template);
		activity.setFormat(Activity.FORMAT_FTL);
		activity.setProjectId(member.getProjectId());
		activity.setEnteredId(member.getUserId());
		activityService.createActivity(activity);
	}

}
