alter table passengers
       add constraint FK_PASSENGER_ROUTE
       foreign key (route_id)
       references routes (id);

alter table passengers
       add constraint FK_PASSENGER_TRANSFER
       foreign key (transfer_id)
       references transfers (id);


alter table routes
       add constraint FK_RETURN_ROUTE_ID
       foreign key (return_route_id)
       references routes (id);

alter table transfers
       add constraint FK_TRANSFER_CAR
       foreign key (car_id)
       references cars (id);

alter table transfers
       add constraint FK_TRANSFER_DRIVER
       foreign key (driver_id)
       references users (id);

alter table transfers
       add constraint FK_TRANSFER_ROUTE
       foreign key (route_id)
       references routes (id);
