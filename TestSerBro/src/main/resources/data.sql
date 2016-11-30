insert into services(Name,id,description,maxdbpernode,bindable) values('testBroker','firstTestBr','service for application development and testing','250','true');

insert into plans(Name,id,description,service,volumeSize,cost) values('one','planOne','Test plan one','firstTestBr','10','12');
insert into plans(Name,id,description,service,volumeSize,cost) values('two','planTwo','Test plan two','firstTestBr','50','20');