alter table routes
  add constraint FK_RETURN_ROUTE_ID foreign key (return_route_id) references routes (id);
