alter table co_passengers
       add constraint FK_CO_PASSENGER
       foreign key (main_passenger_id)
       references passengers (id);

alter table passengers
       add constraint FK_PASSENGER
       foreign key (route_id)
       references routes (id);

alter table routes
       add constraint FK_RETURN_ROUTE_ID
       foreign key (return_route_id)
       references routes (id);
