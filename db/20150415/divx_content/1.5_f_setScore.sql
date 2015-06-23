USE divx_content;

DELIMITER $$
USE `divx_content`$$

DROP PROCEDURE IF EXISTS `f_setScore` $$

CREATE PROCEDURE `f_setScore` (
	IN scoreId INTEGER,
	IN userId INTEGER,
	IN lessonId INTEGER,
	IN lisVal INTEGER,
	IN readVal INTEGER,
	IN recVal INTEGER,
	IN lisDurVal INTEGER,
	IN readDurVal INTEGER,
	IN recDurVal INTEGER
)
BEGIN
	declare retCode INT;
    declare retMessage varchar(255);
    
	DECLARE scoreCount INT default 0;
    declare userCount INT default 0;
    
	declare listenTotal INTEGER default 0;
    declare readTotal INTEGER default 0;
    declare recordTotal INTEGER default 0;
    declare scoreTotal INTEGER default 0;

    if userId <= 0 or lessonId <= 0 then
		set retCode = -2; -- Invalid parameter
		set retMessage = 'Invalid lessonId or userId';
	else
		start transaction;
        
		if (scoreId <= 0) then
			select score_id into scoreId from dcp_score where lesson_id = lessonId and user_id = userId;
		end if;
        
        select count(*) into scoreCount from dcp_score where score_id = scoreId;
		-- select 'scoreCount', scoreCount;
		
        if (scoreCount > 0) then
			update dcp_score 
				set listen = lisVal,
					`read` = readVal,
                    `record` = recVal,
                    score = lisVal * 1 + readVal * 2 + recVal * 3,
                    listenduration = listenduration + lisDurVal,
                    readduration = readduration + readDurVal,
                    recordduration = recordduration + recDurVal,
                    datemodified = now()
				where score_id = scoreId;
		else
			insert into dcp_score (lesson_id, 
								   user_id, 
                                   listen, 
                                   `read`, 
                                   `record`, 
                                   score, 
                                   datecreated, 
                                   datemodified, 
                                   listenduration, 
                                   readduration, 
                                   recordduration)
				values (
					lessonId,
                    userId,
					lisVal,
                    readVal,
                    recVal,
                    lisVal * 1 + readVal * 2 + recVal * 3,
                    now(),
                    now(),
                    lisDurVal,
                    readDurVal,
                    readDurVal
                );
                
                set scoreId = LAST_INSERT_ID();
        end if;		
        
		insert into dcp_scorestat (score_id, 
								   user_id, 
								   datecreated, 
								   listenduration, 
								   readduration, 
								   recordduration)
			values (
				scoreId, 
				userId, 
				now(), 
				lisDurVal, 
				readDurVal, 
				recDurVal
			);
            
		select count(*) into userCount from dcp_totalstat where user_id = userId;
        select  sum(listen), 
				sum(`read`), 
                sum(`record`), 
                sum(score) 
			into listenTotal,
				 readTotal,
                 recordTotal,
                 scoreTotal
			from dcp_score 
            where user_id = userId;
        
        if userCount > 0 then
			update dcp_totalstat set 
				listen = listenTotal,
                `read` = readTotal,
                `record` = recordTotal,
                score = scoreTotal,
                listenduration = listenduration + lisDurVal,
                readduration = readduration + readDurVal,
				recordduration = recordduration + recDurVal,
                datemodified = now()
			where user_id = userId;
        else
			insert into dcp_totalstat (user_id,
									   listen,
                                       `read`,
                                       `record`,
                                       score,
                                       listenduration,
                                       readduration,
                                       recordduration,
                                       datemodified,
                                       datecreated)
				    values (
						userId,
                        listenTotal,
                        readTotal,
                        recordTotal,
                        scoreTotal,
                        lisDurVal,
                        readDurVal,
                        recDurVal,
                        now(),
                        now()
                    );
        end if;
        
        commit;
        set retCode = scoreId;
        set retMessage = "success";
	end if;
    
    select retCode as code, retMessage as message;
END

$$
DELIMITER ;