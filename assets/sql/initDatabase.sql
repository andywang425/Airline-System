DROP database IF EXISTS airline_system;

CREATE database airline_system;

USE airline_system;

CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    uname VARCHAR(20) NOT NULL,
    rname VARCHAR(10) NOT NULL,
    sex CHAR(1) NOT NULL,
    upassword VARCHAR(20) NOT NULL,
    points INT NOT NULL,
    idNum CHAR(18) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

INSERT INTO
    Users
VALUES
    (
        1,
        'Zhang',
        '张三',
        '女',
        '123456',
        0,
        '410223197203281185',
        '13195656565'
    );

INSERT INTO
    Users
VALUES
    (
        2,
        'Li',
        '李四',
        '男',
        '123456',
        0,
        '532524197710208638',
        '13184612706'
    );

INSERT INTO
    Users
VALUES
    (
        3,
        'Zhou',
        '周华',
        '男',
        '123456',
        0,
        '330301196805226215',
        '13394666965'
    );

INSERT INTO
    Users
VALUES
    (
        4,
        'Liu',
        '刘芳芳',
        '女',
        '123456',
        0,
        '622624199312221585',
        '13195656565'
    );

INSERT INTO
    Users
VALUES
    (
        5,
        'Zhao',
        '赵小小',
        '男',
        '123456',
        0,
        '500224198109125947',
        '13195438635'
    );

INSERT INTO
    Users
VALUES
    (
        6,
        'Wu',
        '吴芳',
        '女',
        '123456',
        0,
        '360430197012029825',
        '1010516565'
    );

INSERT INTO
    Users
VALUES
    (
        7,
        'Hua',
        '赵华',
        '男',
        '123456',
        0,
        '450901196909238798',
        '13197605606'
    );

INSERT INTO
    Users
VALUES
    (
        8,
        'Zheng',
        '郑丹丹',
        '女',
        '123456',
        0,
        '37068619621001136X',
        '13185657965'
    );

INSERT INTO
    Users
VALUES
    (
        9,
        'Fang',
        '方舒',
        '男',
        '123456',
        0,
        '310105560816556439',
        '13194326015'
    );

CREATE TABLE Topcontacts (
    cid INT NOT NULL,
    rname VARCHAR(10) NOT NULL,
    telNum VARCHAR(20) NOT NULL
);

INSERT INTO Topcontacts VALUES(1, '张四', '13129999999');

INSERT INTO Topcontacts VALUES(1, '张五', '13128888888');

INSERT INTO Topcontacts VALUES(1, '张六', '13127777777');

INSERT INTO Topcontacts VALUES(2, '李五', '13120999999');

CREATE TABLE Admins (
    aid INT PRIMARY KEY AUTO_INCREMENT,
    aname VARCHAR(20) NOT NULL,
    apassword VARCHAR(10) NOT NULL
);

INSERT INTO Admins VALUES(1, 'admin', 'admin');

INSERT INTO Admins VALUES(2, 'sep20211463', '20211463');

CREATE TABLE Airlines (
    fid INT PRIMARY KEY AUTO_INCREMENT,
    company VARCHAR(10) NOT NULL,
    fltType CHAR(3) NOT NULL,
    flightNumber VARCHAR(10) NOT NULL,
    takeOffTime DATETIME NOT NULL,
    arrivalTime DATETIME NOT NULL,
    departure VARCHAR(10) NOT NULL,
    destination VARCHAR(10) NOT NULL
);

INSERT INTO
    Airlines
VALUES
    (
        1,
        '北方航空',
        'JET',
        'NO123',
        '2022-6-1 09:00:00',
        '2022-6-1 13:00:00',
        '上海',
        '北京'
    );

INSERT INTO
    Airlines
VALUES
    (
        2,
        '北方航空',
        '32M',
        'NO156',
        '2022-7-1 10:00:00',
        '2022-7-1 12:00:00',
        '上海',
        '长水'
    );

INSERT INTO
    Airlines
VALUES
    (
        3,
        '南方航空',
        '333',
        'SO156',
        '2022-7-1 14:00:00',
        '2022-7-1 15:30:00',
        '长水',
        '北京'
    );
    
INSERT INTO
    Airlines
VALUES
    (
        4,
        '西方航空',
        '59J',
        'WE983',
        '2022-7-2 08:00:00',
        '2022-7-2 10:00:00',
        '上海',
        '长水'
    );
    
INSERT INTO
    Airlines
VALUES
    (
        5,
        '东方航空',
        '996',
        'EA560',
        '2022-7-1 09:30:00',
        '2022-7-1 11:00:00',
        '长水',
        '北京'
    );

CREATE TABLE Tickets (
    fid INT NOT NULL PRIMARY KEY,
    economyNum SMALLINT NOT NULL,
    economyPrice INT NOT NULL,
    economyDiscount FLOAT NOT NULL,
    businessNum SMALLINT NOT NULL,
    businessPrice INT NOT NULL,
    businessDiscount FLOAT NOT NULL,
    firstNum SMALLINT NOT NULL,
    firstPrice INT NOT NULL,
    firstDiscount FLOAT NOT NULL
);

INSERT INTO Tickets VALUES(1, 5, 650, 1, 2, 950, 1, 1, 1300, 1);

INSERT INTO
    Tickets
VALUES
    (2, 4, 450, 1, 3, 650, 0.95, 1, 1500, 0.9);

INSERT INTO
    Tickets
VALUES
    (3, 6, 500, 0.95, 0, 800, 0.95, 2, 1300, 0.8);
    
INSERT INTO
    Tickets
VALUES
    (4, 3, 450, 0.95, 1, 800, 0.95, 2, 1300, 0.8);
    
INSERT INTO
    Tickets
VALUES
    (5, 2, 500, 0.95, 1, 800, 0.95, 0, 1300, 0.8);

CREATE TABLE Seats (
	eid INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fid INT NOT NULL,
    classType CHAR(1) NOT NULL,
    seatCode CHAR(3) NOT NULL
);

INSERT INTO Seats VALUES
(1, 1, 'E', '32A');

INSERT INTO Seats VALUES
(2, 1, 'E', '32B');

INSERT INTO Seats VALUES
(3, 1, 'E', '33A');

INSERT INTO Seats VALUES
(4, 1, 'E', '32C');

INSERT INTO Seats VALUES
(5, 1, 'E', '35A');

INSERT INTO Seats VALUES
(6, 1, 'B', '19C');

INSERT INTO Seats VALUES
(7, 1, 'B', '12A');

INSERT INTO Seats VALUES
(8, 1, 'F', '11K');

INSERT INTO Seats VALUES
(9, 2, 'E', '32A');

INSERT INTO Seats VALUES
(10, 2, 'E', '32B');

INSERT INTO Seats VALUES
(11, 2, 'E', '33A');

INSERT INTO Seats VALUES
(12, 2, 'E', '32C');

INSERT INTO Seats VALUES
(13, 2, 'B', '19C');

INSERT INTO Seats VALUES
(14, 2, 'B', '12A');

INSERT INTO Seats VALUES
(15, 2, 'B', '14B');

INSERT INTO Seats VALUES
(16, 2, 'F', '11K');

INSERT INTO Seats VALUES
(17, 3, 'E', '32A');

INSERT INTO Seats VALUES
(18, 3, 'E', '32B');

INSERT INTO Seats VALUES
(19, 3, 'E', '33A');

INSERT INTO Seats VALUES
(20, 3, 'E', '32C');

INSERT INTO Seats VALUES
(21, 3, 'E', '35A');

INSERT INTO Seats VALUES
(22, 3, 'E', '41D');

INSERT INTO Seats VALUES
(23, 3, 'F', '11K');

INSERT INTO Seats VALUES
(24, 3, 'F', '15C');

INSERT INTO Seats VALUES
(25, 4, 'E', '12C');

INSERT INTO Seats VALUES
(26, 4, 'E', '35A');

INSERT INTO Seats VALUES
(27, 4, 'E', '43D');

INSERT INTO Seats VALUES
(28, 4, 'B', '14B');

INSERT INTO Seats VALUES
(29, 4, 'F', '11A');

INSERT INTO Seats VALUES
(30, 4, 'F', '13C');

INSERT INTO Seats VALUES
(31, 5, 'E', '12C');

INSERT INTO Seats VALUES
(32, 5, 'E', '35A');

INSERT INTO Seats VALUES
(33, 5, 'B', '19A');

INSERT INTO Seats VALUES
(34, 5, 'F', '11B');

CREATE TABLE SoldTickets (
    sid INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fid INT NOT NULL,
    userid INT NOT NULL,
    seatNumber CHAR(3) NOT NULL,
    class CHAR(1) NOT NULL,
    finalPrice FLOAT NOT NULL,
    usedPoints INT NOT NULL,
    soldTime DATETIME NOT NULL
);