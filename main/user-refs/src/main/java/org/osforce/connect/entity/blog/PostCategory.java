package org.osforce.connect.entity.blog;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.osforce.connect.entity.system.Project;
import org.osforce.spring4me.commons.collection.CollectionUtil;
import org.osforce.spring4me.entity.IdEntity;

/**
 * @author gavin
 * @since 1.0.0
 * @create Nov 10, 2010 - 4:33:55 PM
 * <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Entity
@Table(name="post_categories")
@Cacheable
public class PostCategory extends IdEntity {
	private static final long serialVersionUID = -1856521070359459275L;
	
	@NotBlank
	private String label;
	private String code;
	private Integer level = 0;
	private Boolean enabled = true;
	// helper
	private Long projectId;
	// refer
	private Project project;
	private List<Post> posts = CollectionUtil.newArrayList();

	public PostCategory() {
	}

	@Column(nullable=false)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public Long getProjectId() {
		if(projectId==null && project!=null) {
			projectId = project.getId();
		}
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="category")
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> blogPosts) {
		this.posts = blogPosts;
	}
}
