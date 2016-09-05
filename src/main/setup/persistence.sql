-- user=root, password=p@ssw0rd
Create database todo;
Create table `task`(
    `id` int(3) NOT NULL AUTO_INCREMENT, 
    `description` VARCHAR(200) NOT NULL, 
    `status` VARCHAR (7), 
    primary key (`id`)
);