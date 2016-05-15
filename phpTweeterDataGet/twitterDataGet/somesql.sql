select distinct(userId) from tbltweets
where hashtag in ('drunk','drank','imdrunk') and userId not in( SELECT distinct(userId) FROM `dataset2`) --2896

select distinct(userId) from tbltweets
where hashtag in ('drunk','drank','imdrunk') and userId in( SELECT distinct(userId) FROM `dataset2`) --237


SELECT hashtag,count(distinct(tweetId)) FROM `tbltweets`
WHERE userId in (
    SELECT distinct(userId) FROM `tbltweets`
    )
    group by hashtag


SELECT hashtag,count(distinct(tweetId))
FROM `tbltweets` tt inner join 
( SELECT distinct(userId) FROM `tbltweets` LIMIT 3900 ) firstDataUsers on tt.userId = firstDataUsers.userId group by hashtag

drank
328
drunk
3377
imdrunk
61
imnotdrunk
13
notdrunk
616
sober
814


SELECT hashtag,count(distinct(tweetId))
FROM `tbltweets` tt inner join
( SELECT distinct(userId) FROM `tbltweets` LIMIT 1000 OFFSET 3900 ) firstDataUsers
 on tt.userId = firstDataUsers.userId group by hashtag

drank
16
drunk
207
imdrunk
10
notdrunk
5
sober
898


SELECT hashtag,count(distinct(tweetId))
FROM `tbltweets` tt 
inner join ( SELECT distinct(userId) FROM `tbltweets` LIMIT 1000 OFFSET 3900 ) firstDataUsers
 on tt.userId = firstDataUsers.userId
wHERE tweet like '%http%'
group by hashtag


DELETE 
FROM `dataset2`
WHERE id in (select ds2.id from (select * from dataset2) ds2
 inner join (SELECT distinct(userId) FROM `tbltweets` LIMIT 1002 OFFSET 3900) ds3
on ds2.userId = ds3.userId)

/**
bunu yanlış yapmıştık
**/
INSERT INTO `dataset3`(`tweetId`,`userId`,  `hashtag`, `tweet`) 
select distinct(tt.tweetId), tt.userId, tt.hashtag, tt.tweet from `tbltweets` tt 
inner join ( SELECT distinct(userId) FROM `tbltweets` LIMIT 1002 OFFSET 3900 ) firstDataUsers 
on tt.userId = firstDataUsers.userId

INSERT INTO `dataset3`(`tweetId`,`userId`,  `hashtag`, `tweet`) 
select distinct(tt.tweetId), tt.userId, tt.hashtag, tt.tweet from `tbltweets` tt
inner join ( SELECT distinct(userId) FROM `tbltweets` LIMIT 1002 OFFSET 3900 ) firstDataUsers
	on tt.userId = firstDataUsers.userId
where tt.hashtag in('drunk','drank','imdrunk')


select tweetId,count(distinct(hashTag)) from tblTweets tt inner join (
    SELECT userId, count(distinct(tweetId)) FROM `tbltweets` 
    group by userId
    having count(distinct(tweetId)) > 1
) users on tt. userId = users.userId
group by tweetId
having count(distinct(hashTag)) > 1


SELECT ds2.tweetId,ds2.`userId`,ds2.`hashtag`,ds2.`tweet`
FROM `dataset2` ds2 inner join dataset1 ds1 
on ds2.userId = ds1.userId
WHERE  ds2.tweet not like '%drunk%'
and ds2.tweet not like '%drank%'
and ds2.tweet not like '%imdrunk%'
group by ds2.tweetId


INSERT INTO `dataset2v2`(`tweetId`, `userId`, `hashtag`, `tweet`) 
SELECT ds2.tweetId,ds2.`userId`,ds2.`hashtag`,ds2.`tweet` FROM `dataset1` ds2 WHERE ds2.hashtag in ('drunk','drank','imdrunk')