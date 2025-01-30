DROP TABLE IF EXISTS custom_property, item, item_custom_property, custom_property_bindings, person, person_custom_property;

CREATE TABLE item
(
    id         bigint NOT NULL PRIMARY KEY,
    created_at DATE   NOT NULL,
    updated_at DATE   NOT NULL
);

CREATE TABLE person
(
    id         bigint NOT NULL PRIMARY KEY,
    created_at DATE   NOT NULL,
    updated_at DATE   NOT NULL
);


CREATE TABLE custom_property
(
    id   bigint       NOT NULL PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE custom_property_bindings
(
    class_name         varchar NOT NULL,
    custom_property_id bigint  NOT NULL,
    enabled            boolean NOT NULL,
    created_at         timestamp    NOT NULL,
    updated_at         timestamp    NOT NULL,
    CONSTRAINT primary_key primary key (class_name, custom_property_id),
    CONSTRAINT fk_custom_property_id FOREIGN KEY (custom_property_id) REFERENCES custom_property (id)
        ON UPDATE CASCADE ON DELETE CASCADE


);

CREATE TABLE item_custom_property
(
    object_id          bigint NOT NULL,
    custom_property_id bigint NOT NULL,
    long_value         bigint,
    integer_value      INT,
    double_value       DOUBLE PRECISION,
    boolean_value      BOOLEAN,
    string_value       TEXT,
    PRIMARY KEY (object_id, custom_property_id),
    CONSTRAINT fk_item FOREIGN KEY (object_id) REFERENCES item (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_cp FOREIGN KEY (custom_property_id) REFERENCES custom_property (id)
);

CREATE TABLE person_custom_property
(
    object_id          bigint NOT NULL,
    custom_property_id bigint NOT NULL,
    long_value         bigint,
    integer_value      INT,
    double_value       DOUBLE PRECISION,
    boolean_value      BOOLEAN,
    string_value       TEXT,
    PRIMARY KEY (object_id, custom_property_id),
    CONSTRAINT fk_person FOREIGN KEY (object_id) REFERENCES person (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_cp FOREIGN KEY (custom_property_id) REFERENCES custom_property (id)
);

INSERT INTO item
VALUES (1, now(), now()),
       (2, now(), now()),
       (3, now(), now());

INSERT INTO person
VALUES (1, now(), now());


INSERT INTO custom_property
VALUES (1, 'CODE_SIZE', 'STRING'),
       (2, 'ADDITIONAL_SIZE', 'INTEGER'),
       (3, 'IS_ITEM_DAMAGED', 'BOOLEAN');

INSERT INTO custom_property_bindings
VALUES ('Item.class', 1, true, now(), now()),
       ('Item.class', 2, true, now(), now()),
       ('Item.class', 3, true, now(), now()),
       ('Person.class', 1, true, now(), now());