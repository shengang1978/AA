package org.osforce.connect.service.commons.impl;

import java.util.List;

import org.osforce.connect.dao.commons.TemplateDao;
import org.osforce.connect.dao.system.ProjectCategoryDao;
import org.osforce.connect.entity.commons.Template;
import org.osforce.connect.entity.system.ProjectCategory;
import org.osforce.connect.service.commons.TemplateService;
import org.osforce.spring4me.dao.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Feb 13, 2011 - 10:16:18 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

	private TemplateDao templateDao;
	private ProjectCategoryDao projectCategoryDao;
	
	public TemplateServiceImpl() {
	}
	
	@Autowired
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	@Autowired
	public void setProjectCategoryDao(ProjectCategoryDao projectCategoryDao) {
		this.projectCategoryDao = projectCategoryDao;
	}
	
	public Template getTemplate(Long templateId) {
		return templateDao.get(templateId);
	}
	
	public Template getTemplate(Long categoryId, String templateCode) {
		return templateDao.findTemplate(categoryId, templateCode);
	}

	public void createTemplate(Template template) {
		updateTemplate(template);
	}

	public void updateTemplate(Template template) {
		if(template.getCategoryId()!=null) {
			ProjectCategory category = projectCategoryDao.get(template.getCategoryId());
			template.setCategory(category);
		}
		if(template.getId()==null) {
			templateDao.save(template);
		} else {
			templateDao.update(template);
		}
	}

	public void deleteTemplate(Long templateId) {
		templateDao.delete(templateId);
	}

	public Page<Template> getTemplatePage(Page<Template> page, Long siteId) {
		return templateDao.findTemplatePage(page, siteId);
	}
	
	public List<Template> getTemplateList(Long siteId) {
		return templateDao.findTemplateList(siteId);
	}
}
