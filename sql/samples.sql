use xnerd;
/*
SELECT g.name,
        g.owned,
        g.create_ts,
       count(v.game_id) as vote_count
FROM
  xnerd.games g
left outer join xnerd.votes v on g.game_id=v.game_id
where g.owned = 0
group by g.name
order by vote_count desc
*/

select
  count(v.game_id) as vote_count
from
  votes v
where
  v.game_id=1
