insert into skill (skill_name) values('creativity')
insert into skill (skill_name) values('java')

insert into job_offer(title_of_position, company_name, region, education_level, post_date, skill_level) values('programmer','accenture','athens',1,'2020-02-01',0)
--insert into job_offer(title_of_position, company_name, region, education_level, post_date) values('Javaprogrammer','accenture','athens',0,'2020-02-08')

insert into job_skill(job_id, skill_id) values(1,1)
insert into job_skill(job_id, skill_id) values(1,2)