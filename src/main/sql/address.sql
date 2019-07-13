-- ------------------------------------------------------------------------------------------------------ print location
SELECT s.txt                                                             AS 'TXT',
       s.bin                                                             AS 'BIN',
       CONCAT(s.o, ' -> ', (CASE s.o WHEN '00' THEN 'BE' ELSE 'LE' END)) AS '(1) ORDER',
       CONV(HEX(REVERSE(UNHEX(s.t))), 16, 10)                            AS '(4) TYPE',
       s.x                                                               AS '(8) x',
       s.y                                                               AS '(8) y',
       s.h
FROM (SELECT ST_AsText(location)                     AS txt,
             ST_AsWKB(location)                      AS bin,
             SUBSTRING(HEX(location) FROM 9 FOR 2)   AS o, -- byte order
             SUBSTRING(HEX(location) FROM 11 FOR 8)  AS t, -- geometry type
             SUBSTRING(HEX(location) FROM 19 FOR 16) AS x, -- x
             SUBSTRING(HEX(location) FROM 35 FOR 16) AS y, -- y
             HEX(location)                           AS h
      FROM address
      ORDER BY address_id ASC) AS s
;