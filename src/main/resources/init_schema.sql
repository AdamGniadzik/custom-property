DROP TABLE IF EXISTS custom_property, item, item_cp;

CREATE TABLE item
(
    id         INT  NOT NULL PRIMARY KEY,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
);

CREATE TABLE custom_property
(
    id       INT          NOT NULL PRIMARY KEY,
    code     VARCHAR(100) NOT NULL,
    type     VARCHAR(100) NOT NULL,
    typeEnum ENUM('LONG', 'BOOLEAN', 'STRING', 'INTEGER', 'DOUBLE')
);

CREATE TABLE item_cp
(
    object_id     INT NOT NULL,
    cp_id         INT NOT NULL,
    long_value    NUMERIC,
    integer_value NUMERIC,
    double_value  DOUBLE PRECISION,
    boolean_value BOOLEAN,
    string_value  TEXT,
    PRIMARY KEY (object_id, cp_id),
    CONSTRAINT fk_item FOREIGN KEY (object_id) REFERENCES item (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_cp FOREIGN KEY (cp_id) REFERENCES custom_property (id)
);

INSERT INTO item
VALUES (1, now(), now()),
       (2, now(), now()),
       (3, now(), now());


INSERT INTO custom_property
VALUES (1, 'CODE_SIZE', 'STRING', 'STRING'),
       (2, 'ADDITIONAL_SIZE', 'INTEGER', 'INTEGER'),
       (3, 'IS_ITEM_DAMAGED', 'BOOLEAN', 'BOOLEAN');