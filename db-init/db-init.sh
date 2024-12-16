#!/bin/bash
set -e

# Connect to the docker database and create tables
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER docker WITH PASSWORD 'pswd-docker';
    CREATE DATABASE docker;
    ALTER DATABASE docker OWNER TO docker;
    \c docker

    -- =============================
    -- User Account & Info Tables
    -- =============================

    -- user account
    create table if not exists account (
        id serial unique,
        email varchar(50) unique not null,
        password varchar(60) not null,
        active boolean not null,
        activationCode int,
        primary key (id)
    );

    -- profile
    create table if not exists profile (
        email varchar(50) unique not null references account(email),
        firstName varchar(20),
        lastName varchar(20),
        cookingLevel varchar(20),
        favoriteDish varchar(40),
        favoriteCuisine varchar(40),
        primary key (email)
    );

    -- authority
    create table if not exists authority (
        email varchar(50) references account(email) on delete cascade,
        role varchar(20) not null,
        primary key (email, role)
    );

    -- =============================
    -- Recipe Required Fields Tables
    -- =============================

    -- info
    create table if not exists info (
        recipeID serial,
        email varchar(50) references account(email),
        recipeName varchar(40),
        description varchar(1000),
        servings numeric,
        unitOfServings varchar(20), --change once prepop made
        prepMin int,
        prepHr int,
        cookMin int,
        cookHr int,
        totalMin int, -- trigger
        totalHr int,
        primary key (recipeID)
    );

    -- direction
    create table if not exists direction (
        recipeID int references info(recipeID) on delete cascade,
        stepNum int,
        direction varchar(300),
        method varchar(30), --change once prepop made
        temp int,
        level varchar(10), --change once prepop made
        primary key(recipeID, stepNum)
    );

    -- ingredient
    create table if not exists ingredient (
        recipeID int references info(recipeID) on delete cascade,
        component varchar(30),
        quantity int,
        measurement varchar(20), --change once prepop made
        preparation varchar(20), --change once prepop made
        primary key (recipeID, component)
    );

    -- =============================
    -- Recipe Community Tables
    -- =============================

    -- comment
    create table if not exists comment (
        recipeID int references info(recipeID) on delete cascade,
        commentID serial, --used for PK
        commentEmail varchar(50) references account(email) on delete cascade, --technically do they link?
        commentText varchar(500),
        primary key(recipeID, commentID)
    );

    --rating
    create table if not exists rating (
        recipeID int references info(recipeID) on delete cascade,
        raterEmail varchar(50) references account(email) on delete cascade,
        ratingValue int,
        primary key (recipeID, raterEmail)
    );

    -- =============================
    -- Recipe Option Fields Tables
    -- =============================

    -- ======
    -- Categories
    -- ======

    -- holiday
    create table if not exists holiday (
        recipeID int references info(recipeID) on delete cascade,
        holiday varchar(30),
        primary key (recipeID, holiday)
    );

    -- meal type
    create table if not exists mealType (
        recipeID int references info(recipeID) on delete cascade,
        mealType varchar(30),
        primary key (recipeID, mealType)
    );

    -- cuisine
    create table if not exists cuisine (
        recipeID int references info(recipeID) on delete cascade,
        cuisine varchar(30),
        primary key (recipeID, cuisine)
    );

    -- allergen
    create table if not exists allergen (
        recipeID int references info(recipeID) on delete cascade,
        allergen varchar(30),
        primary key (recipeID, allergen)
    );

    -- diet type
    create table if not exists dietType (
        recipeID int references info(recipeID) on delete cascade,
        dietType varchar(30),
        primary key (recipeID, dietType)
    );

    -- equipment
    create table if not exists equipment (
        recipeID int references info(recipeID) on delete cascade,
        equipment varchar(30),
        primary key (recipeID, equipment)
    );

    -- ======
    -- Media
    -- ======

    -- photo
    create table if not exists photo (
        recipeID int references info(recipeID) on delete cascade,
        fileLocation varchar(125),
        primary key (recipeID)
    );

    -- link
    create table if not exists link (
        recipeID int references info(recipeID) on delete cascade,
        link varchar(125),
        primary key (recipeID)
    );


    -- ======
    -- Other
    -- ======

    -- notes
    create table if not exists notes (
        recipeID int references info(recipeID) on delete cascade,
        notes varchar(2500),
        primary key (recipeID)
    );

    -- user substitutions
    create table if not exists userSubs (
        recipeID int references info(recipeID) on delete cascade,
        componentO varchar(30),
        measurementO varchar(20), --change once prepop made
        preparationO varchar(20), --change once prepop made
        componentS varchar(30),
        measurementS varchar(20), --change once prepop made
        preparationS varchar(20), --change once prepop made
        primary key(recipeID, componentO, componentS)
    );

    -- =============================
    -- Recipe Set Up Tables
    -- =============================

    -- knownSubs
    create table if not exists knownSubs (
        componentO varchar(30),
        measurementO varchar(20), --change once prepop made
        preparationO varchar(20), --change once prepop made
        componentS varchar(30),
        measurementS varchar(20), --change once prepop made
        preparationS varchar(20), --change once prepop made
        primary key(componentO, componentS)
    );

    -- glossary
    create table if not exists glossary (
        term varchar(30),
        definition varchar(300),
        primary key(term, definition)
    );

    alter table account owner to docker;
    alter table authority owner to docker;
    alter table profile owner to docker;
    alter table info owner to docker;
    alter table direction owner to docker;
    alter table ingredient owner to docker;
    alter table comment owner to docker;
    alter table rating owner to docker;
    alter table holiday owner to docker;
    alter table mealType owner to docker;
    alter table cuisine owner to docker;
    alter table allergen owner to docker;
    alter table dietType owner to docker;
    alter table equipment owner to docker;
    alter table photo owner to docker;
    alter table link owner to docker;
    alter table notes owner to docker;
    alter table userSubs owner to docker;
    alter table knownSubs owner to docker;
    alter table glossary owner to docker;

EOSQL
