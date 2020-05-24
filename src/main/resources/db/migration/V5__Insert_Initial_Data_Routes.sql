insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route) VALUES (1, 400, 'TIMISOARA', null, 10, 75, 'BUDAPESTA', 1);
insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route, return_route_id) VALUES (2, 400, 'BUDAPESTA', null, 10, 75, 'TIMISOARA', 1, 1);
update ROUTES SET return_route_id = 2 WHERE id = 1;
insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route) VALUES (3, 400, 'ARAD', null, 30, 150, 'BELGRAD', 1);
insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route, return_route_id) VALUES (4, 400, 'BELGRAD', null, 30, 150, 'ARAD', 1, 3);
update ROUTES SET return_route_id = 4 WHERE id = 3;
insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route) VALUES (5, 100, 'TIMISOARA', null, 10, 50, 'ARAD', 0);
insert into ROUTES (id, distance_in_km, from_location, notes, price_in_eur, price_in_ron, to_location, is_transit_route, return_route_id) VALUES (6, 100, 'ARAD', null, 10, 50, 'TIMISOARA', 0, 5);
update ROUTES SET return_route_id = 6 WHERE id = 5;
