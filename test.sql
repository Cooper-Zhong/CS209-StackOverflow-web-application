select count(*)
from answers;


select qt.tag_name, avg(q.view_count) as average_view_count
from questions_tags qt
         join questions q on qt.question_id = q.question_id
where qt.tag_name in (select tag_name
                      from tags)
group by qt.tag_name
order by average_view_count desc;


select qt.tag_name, avg(q.view_count) as average_view_count
from questions_tags qt
         join questions q on qt.question_id = q.question_id
group by qt.tag_name
order by average_view_count desc
limit 15;


select count(*)
from tags;


-- 给定一个问题，找到与该问题相关的问题，按照相关性排序
create or replace function find_similar_topics(input_topic_name TEXT, topK INTEGER default 10)
    returns TABLE
            (
                similar_topic      TEXT,
                jaccard_similarity NUMERIC
            )
as
$$
begin
    return query
        with tag_counts as (select question_id, count(*) as tag_count
                            from questions_tags
                            group by question_id),
             tag_pairs as (select qt1.question_id, qt1.tag_name as tag1, qt2.tag_name as tag2
                           from questions_tags qt1
                                    join questions_tags qt2
                                         on qt1.question_id = qt2.question_id and qt1.tag_name < qt2.tag_name),
             tag_jaccard as (select tp.tag1,
                                    tp.tag2,
                                    count(*)      as intersection_count,
                                    tc1.tag_count as count1,
                                    tc2.tag_count as count2,
                                    case
                                        when (tc1.tag_count + tc2.tag_count - count(*)) = 0
                                            then 0 -- Handling division by zero
                                        else count(*)::NUMERIC / (tc1.tag_count + tc2.tag_count - count(*)) -- Cast to NUMERIC
                                        end       as jaccard_similarity_alias
                             from tag_pairs tp
                                      join tag_counts tc1 on tp.question_id = tc1.question_id
                                      join tag_counts tc2 on tp.question_id = tc2.question_id and tp.tag1 <> tp.tag2
                             group by tp.tag1, tp.tag2, tc1.tag_count, tc2.tag_count)
        select case
                   when tag1 = input_topic_name then tag2
                   else tag1
                   end                  as similar_topic,
               jaccard_similarity_alias as jaccard_similarity
        from tag_jaccard
        where (tag1 = input_topic_name or tag2 = input_topic_name)
        order by jaccard_similarity_alias desc
        limit topK;

end;
$$ language plpgsql;



select *
from find_similar_topics('multithreading');

