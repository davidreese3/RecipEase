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
        email varchar(50) references account(email) on delete cascade on update cascade,
        firstName varchar(20),
        lastName varchar(20),
        cookingLevel varchar(20),
        favoriteDish varchar(40),
        favoriteCuisine varchar(40),
        primary key (email)
    );

    -- authority
    create table if not exists authority (
        email varchar(50) references account(email) on delete cascade on update cascade,
        role varchar(20) not null,
        primary key (email, role)
    );

    -- =============================
    -- Recipe Required Fields Tables
    -- =============================

    -- info
    create table if not exists info (
        recipeID serial,
        email varchar(50) references account(email) on update cascade,
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
        commentEmail varchar(50) references account(email) on delete cascade on update cascade, --technically do they link?
        commentText varchar(500),
        primary key(recipeID, commentID)
    );

    --rating
    create table if not exists rating (
        recipeID int references info(recipeID) on delete cascade,
        raterEmail varchar(50) references account(email) on delete cascade on update cascade,
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

    -- Insertion script with accented and non-accented terms
    INSERT INTO glossary (term, definition) VALUES
    ('Al dente', 'Italian term meaning "to the tooth," used to describe pasta or rice cooked until it offers slight resistance when bitten.'),
    ('Bain-marie', 'A water bath used to gently heat or cook food, often for custards, sauces, or melting chocolate.'),
    ('Bake', 'To cook food in an oven using dry heat.'),
    ('Baste', 'To pour or brush liquid (like melted butter or pan juices) over food during cooking to keep it moist.'),
    ('Blanch', 'To briefly cook food in boiling water or steam, then quickly cool it in ice water.'),
    ('Blend', 'To combine two or more ingredients until smooth and uniform.'),
    ('Boil', 'To cook food in water or liquid at its boiling point (100°C/212°F).'),
    ('Braise', 'To cook food slowly in a small amount of liquid in a covered pot.'),
    ('Broil', 'To cook food directly under or over a heat source, typically in an oven.'),
    ('Butterfly', 'To cut food (usually meat or shrimp) almost in half, leaving the two halves connected.'),
    ('Caramelize', 'To cook sugar until it becomes a golden brown syrup.'),
    ('Chop', 'To cut food into smaller, irregular pieces.'),
    ('Clarify', 'To make a liquid clear by removing impurities, such as clarifying butter or stock.'),
    ('Coddle', 'To cook food gently in water just below boiling.'),
    ('Cream', 'To beat ingredients (like butter and sugar) until light and fluffy.'),
    ('Cure', 'To preserve food using salt, sugar, or smoke.'),
    ('Cut in', 'To mix solid fat (like butter) into flour using a pastry blender, knife, or fingers.'),
    ('Dash', 'A very small amount of a liquid or spice.'),
    ('Deglaze', 'To loosen and dissolve browned bits of food from the bottom of a pan by adding liquid.'),
    ('Dice', 'To cut food into small, even cubes.'),
    ('Dock', 'To pierce pastry dough with a fork to prevent it from puffing up during baking.'),
    ('Dredge', 'To coat food lightly with flour, breadcrumbs, or another dry ingredient before cooking.'),
    ('Emulsify', 'To combine two liquids that usually don’t mix (e.g., oil and vinegar) into a stable mixture.'),
    ('Extract', 'A concentrated flavoring, such as vanilla or almond extract.'),
    ('Ferment', 'To allow food or drink to sit and develop flavor through natural microbial activity.'),
    ('Fillet', 'To remove the bones from meat or fish.'),
    ('Flambé', 'To ignite alcohol in a dish for dramatic effect and flavor enhancement.'),
    ('Flambe', 'To ignite alcohol in a dish for dramatic effect and flavor enhancement.'),
    ('Fold', 'To gently combine ingredients, usually to preserve air in a mixture.'),
    ('Fry', 'To cook food in hot fat or oil.'),
    ('Ganache', 'A mixture of chocolate and cream, often used as a glaze or filling.'),
    ('Garnish', 'To decorate a dish with small edible items.'),
    ('Glaze', 'A shiny coating applied to food, often made of sugar, jelly, or sauce.'),
    ('Grate', 'To shred food into small pieces using a grater.'),
    ('Grill', 'To cook food on a grill over direct heat.'),
    ('Herbs', 'Leaves of plants used for seasoning, such as basil, thyme, and cilantro.'),
    ('Hull', 'To remove the outer covering, such as the leafy top of a strawberry.'),
    ('Infuse', 'To steep an ingredient in a liquid to extract flavor.'),
    ('Ice', 'To cool food quickly by placing it in ice water.'),
    ('Julienne', 'To cut food into thin, matchstick-sized strips.'),
    ('Knead', 'To work dough by folding, pressing, and stretching.'),
    ('Lard', 'To insert strips of fat into lean meat before cooking.'),
    ('Leaven', 'To cause dough to rise using a leavening agent like yeast or baking powder.'),
    ('Marinate', 'To soak food in a flavored liquid before cooking.'),
    ('Mince', 'To chop food into very small pieces.'),
    ('Mix', 'To combine ingredients thoroughly.'),
    ('Mise en place', 'French term meaning "everything in its place," referring to preparing all ingredients before cooking.'),
    ('Nap', 'To coat food lightly with a sauce.'),
    ('Pan-fry', 'To cook food in a small amount of fat in a pan.'),
    ('Parboil', 'To partially cook food by boiling.'),
    ('Poach', 'To cook food gently in simmering liquid.'),
    ('Proof', 'To let yeast dough rise before baking.'),
    ('Puree', 'To blend food into a smooth, creamy consistency.'),
    ('Reduce', 'To cook a liquid until its volume decreases and its flavor intensifies.'),
    ('Roast', 'To cook food in the oven with dry heat.'),
    ('Roux', 'A mixture of fat and flour used to thicken sauces.'),
    ('Sauté', 'To cook food quickly in a small amount of oil or fat.'),
    ('Saute', 'To cook food quickly in a small amount of oil or fat.'),
    ('Scald', 'To heat liquid just below boiling.'),
    ('Score', 'To cut shallow lines into food for decoration or to help it cook evenly.'),
    ('Sear', 'To brown the surface of food quickly at high heat.'),
    ('Season', 'To enhance the flavor of food with salt, spices, or herbs.'),
    ('Simmer', 'To cook food in liquid just below boiling.'),
    ('Skim', 'To remove fat or foam from the surface of a liquid.'),
    ('Steam', 'To cook food using steam from boiling water.'),
    ('Steep', 'To soak dry ingredients in liquid to extract flavor.'),
    ('Temper', 'To gently heat an ingredient (like eggs or chocolate) to prevent curdling or seizing.'),
    ('Tenderize', 'To make meat more tender by pounding or marinating.'),
    ('Toast', 'To brown food by applying dry heat.'),
    ('Toss', 'To mix ingredients lightly by lifting and dropping them.'),
    ('Umami', 'A savory flavor, one of the five basic tastes.'),
    ('Whisk', 'To beat ingredients together, incorporating air for a lighter texture.'),
    ('Whip', 'To beat ingredients to increase volume and incorporate air.'),
    ('Zest', 'The outer colored layer of citrus fruit peel, used for flavor.');

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
