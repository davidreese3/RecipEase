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
        password varchar(97) not null,
        active boolean not null,
        verificationCode int,
        primary key (id)
    );


    -- profile
    create table if not exists profile (
        id int references account(id) on delete cascade on update cascade,
        firstName varchar(20),
        lastName varchar(20),
        cookingLevel varchar(20),
        favoriteDish varchar(40),
        favoriteCuisine varchar(40),
        primary key (id)
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
        recipeId serial,
        userId int references account(id) on delete cascade on update cascade,
        name varchar(40),
        description varchar(1000),
        yield varchar(12),
        unitOfYield varchar(20),
        prepMin int,
        prepHr int,
        processMin int,
        processHr int,
        totalMin int,
        totalHr int,
        fts_document tsvector generated always as (
                setweight(to_tsvector('english', name), 'A') ||
                setweight(to_tsvector('english', description), 'C')
            ) stored,
        staffTrending boolean,
        primary key (recipeId)
    );

    create index info_fts_idx on info using gin(fts_document);

    -- ingredient
    create table if not exists ingredient (
        recipeId int references info(recipeId) on delete cascade,
        component varchar(45),
        quantity varchar(12),
        measurement varchar(20),
        preparation varchar(20),
        primary key (recipeId, component, preparation)
    );

    -- direction
    create table if not exists direction (
        recipeId int references info(recipeId) on delete cascade,
        stepNum int,
        direction varchar(300),
        method varchar(30),
        temp int,
        level varchar(10),
        primary key(recipeId, stepNum)
    );

    -- =============================
    -- Recipe Community Tables
    -- =============================

    -- comment
    create table if not exists comment (
        recipeId int references info(recipeId) on delete cascade,
        commentId serial, --used for PK
        commentUserId int references account(id) on delete cascade,
        commentText varchar(500),
        primary key(commentID)
    );

    --rating
    create table if not exists rating (
        recipeId int references info(recipeId) on delete cascade,
        ratingUserId int references account(id) on delete cascade,
        ratingValue int,
        primary key (recipeId, ratingUserId)
    );

    -- =============================
    -- Recipe Option Fields Tables
    -- =============================

    -- ======
    -- Tags
    -- ======

    create table if not exists tags (
        recipeId int references info(recipeId) on delete cascade,
        tagField varchar(30),
        tagValue varchar(30),
        primary key (recipeId, tagField, tagValue)
    );

    -- ======
    -- Types
    -- ======

    -- variation
    create table if not exists variation (
        originalRecipeId int references info(recipeId) on delete cascade,
        variationRecipeId int references info(recipeId) on delete cascade,
        primary key (originalRecipeId, variationRecipeId)
    );

    -- ======
    -- Media
    -- ======

    -- photo
    create table if not exists photo (
        recipeId int references info(recipeId) on delete cascade,
        fileName varchar(125),
        primary key (recipeId)
    );

    -- link
    create table if not exists link (
        recipeId int references info(recipeId) on delete cascade,
        link varchar(125),
        primary key (recipeId, link)
    );


    -- ======
    -- Other
    -- ======

    -- note
    create table if not exists note (
        recipeId int references info(recipeId) on delete cascade,
        note varchar(2500),
        primary key (recipeId)
    );

    -- bookmark
    create table if not exists bookmark (
        userId int references account(id) on delete cascade,
        recipeId int references info(recipeId) on delete cascade,
        primary key (userId, recipeId)
    );

    -- user substitutions
    create table if not exists userSubs (
        recipeId int references info(recipeId) on delete cascade,
        originalComponent varchar(45),
        originalQuantity varchar(12),
        originalMeasurement varchar(20),
        originalPreparation varchar(20),
        substitutedComponent varchar(45),
        substitutedQuantity varchar(12),
        substitutedMeasurement varchar(20),
        substitutedPreparation varchar(20),
        primary key(recipeId, originalComponent, originalPreparation, substitutedComponent, substitutedPreparation)
    );

    -- Help tickets
    create table if not exists ticket(
        id serial unique,
        email varchar(50),
        classifier varchar(20),
        subject varchar(40),
        note varchar(280),
        solved boolean,
        primary key(id)
    );

    -- =============================
    -- Recipe Set Up Tables
    -- =============================

    -- Common Subs
    create table if not exists commonSubs (
        originalComponent varchar(45),
        originalQuantity varchar(12),
        originalMeasurement varchar(20),
        originalPreparation varchar(20),
        substitutedComponent varchar(45),
        substitutedQuantity varchar(12),
        substitutedMeasurement varchar(20),
        substitutedPreparation varchar(20),
        primary key(originalComponent, substitutedComponent)
    );

    -- glossary
    create table if not exists glossary (
        term varchar(30),
        definition varchar(300),
        relatedForms varchar(100),
        primary key(term, definition)
    );

    --Insert glossary terms and definitions
    INSERT INTO glossary(term, definition, relatedForms) VALUES
        ('Al dente', 'Italian term meaning "to the tooth," used to describe pasta or rice cooked until it offers slight resistance when bitten.', 'al dente'),
        ('Bain-marie', 'A water bath used to gently heat or cook food, often for custards, sauces, or melting chocolate.', 'bain-marie'),
        ('Bake', 'To cook food in an oven using dry heat.', 'bake, baked, baking'),
        ('Baste', 'To pour or brush liquid (like melted butter or pan juices) over food during cooking to keep it moist.', 'baste, basted, basting'),
        ('Blanch', 'To briefly cook food in boiling water or steam, then quickly cool it in ice water.', 'blanch, blanched, blanching'),
        ('Blend', 'To combine two or more ingredients until smooth and uniform.', 'blend, blended, blending'),
        ('Boil', 'To cook food in water or liquid at its boiling point (100°C/212°F).', 'boil, boiled, boiling'),
        ('Braise', 'To cook food slowly in a small amount of liquid in a covered pot.', 'braise, braised, braising'),
        ('Broil', 'To cook food directly under or over a heat source, typically in an oven.', 'broil, broiled, broiling'),
        ('Butterfly', 'To cut food (usually meat or shrimp) almost in half, leaving the two halves connected.', 'butterfly, butterflied, butterflying'),
        ('Caramelize', 'To cook sugar until it becomes a golden brown syrup.', 'caramelize, caramelized, caramelizing'),
        ('Chop', 'To cut food into smaller, irregular pieces.', 'chop, chopped, chopping'),
        ('Clarify', 'To make a liquid clear by removing impurities, such as clarifying butter or stock.', 'clarify, clarified, clarifying'),
        ('Coddle', 'To cook food gently in water just below boiling.', 'coddle, coddled, coddling'),
        ('Cream', 'To beat ingredients (like butter and sugar) until light and fluffy.', 'cream, creamed, creaming'),
        ('Cure', 'To preserve food using salt, sugar, or smoke.', 'cure, cured, curing'),
        ('Deglaze', 'To loosen and dissolve browned bits of food from the bottom of a pan by adding liquid.', 'deglaze, deglazed, deglazing'),
        ('Dock', 'To pierce pastry dough with a fork to prevent it from puffing up during baking.', 'dock, docked, docking'),
        ('Dredge', 'To coat food lightly with flour, breadcrumbs, or another dry ingredient before cooking.', 'dredge, dredged, dredging'),
        ('Emulsify', 'To combine two liquids that usually don’t mix (e.g., oil and vinegar) into a stable mixture.', 'emulsify, emulsified, emulsifying'),
        ('Extract', 'A concentrated flavoring, such as vanilla or almond extract.', 'extract, extracted, extracting'),
        ('Ferment', 'To allow food or drink to sit and develop flavor through natural microbial activity.', 'ferment, fermented, fermenting'),
        ('Fillet', 'To remove the bones from meat or fish.', 'fillet, filleted, filleting'),
        ('Flambé', 'To ignite alcohol in a dish for dramatic effect and flavor enhancement.', 'flambé, flambéed, flambéing'),
        ('Fold', 'To gently combine ingredients, usually to preserve air in a mixture.', 'fold, folded, folding'),
        ('Ganache', 'A mixture of chocolate and cream, often used as a glaze or filling.', 'ganache'),
        ('Garnish', 'To decorate a dish with small edible items.', 'garnish, garnished, garnishing'),
        ('Glaze', 'A shiny coating applied to food, often made of sugar, jelly, or sauce.', 'glaze, glazed, glazing'),
        ('Grate', 'To shred food into small pieces using a grater.', 'grate, grated, grating'),
        ('Grill', 'To cook food on a grill over direct heat.', 'grill, grilled, grilling'),
        ('Herbs', 'Leaves of plants used for seasoning, such as basil, thyme, and cilantro.', 'herbs'),
        ('Hull', 'To remove the outer covering, such as the leafy top of a strawberry.', 'hull, hulled, hulling'),
        ('Infuse', 'To steep an ingredient in a liquid to extract flavor.', 'infuse, infused, infusing'),
        ('Ice', 'To cool food quickly by placing it in ice water.', 'ice, iced, icing'),
        ('Julienne', 'To cut food into thin, matchstick-sized strips.', 'julienne, julienned, julienning'),
        ('Knead', 'To work dough by folding, pressing, and stretching.', 'knead, kneaded, kneading'),
        ('Lard', 'To insert strips of fat into lean meat before cooking.', 'lard, larded, larding'),
        ('Leaven', 'To cause dough to rise using a leavening agent like yeast or baking powder.', 'leaven, leavened, leavening'),
        ('Marinate', 'To soak food in a flavored liquid before cooking.', 'marinate, marinated, marinating'),
        ('Mise en place', 'French term meaning "everything in its place," referring to preparing all ingredients before cooking.', 'mise en place'),
        ('Nap', 'To coat food lightly with a sauce.', 'nap, napped, napping'),
        ('Pan-fry', 'To cook food in a small amount of fat in a pan.', 'pan-fry, pan-fried, pan-frying'),
        ('Parboil', 'To partially cook food by boiling.', 'parboil, parboiled, parboiling'),
        ('Poach', 'To cook food gently in simmering liquid.', 'poach, poached, poaching'),
        ('Proof', 'To let yeast dough rise before baking.', 'proof, proofed, proofing'),
        ('Puree', 'To blend food into a smooth, creamy consistency.', 'puree, pureed, pureeing'),
        ('Reduce', 'To cook a liquid until its volume decreases and its flavor intensifies.', 'reduce, reduced, reducing'),
        ('Roast', 'To cook food in the oven with dry heat.', 'roast, roasted, roasting'),
        ('Roux', 'A mixture of fat and flour used to thicken sauces.', 'roux'),
        ('Sauté', 'To cook food quickly in a small amount of oil or fat.', 'sauté, sautéed, sautéing'),
        ('Scald', 'To heat liquid just below boiling.', 'scald, scalded, scalding'),
        ('Score', 'To cut shallow lines into food for decoration or to help it cook evenly.', 'score, scored, scoring'),
        ('Season', 'To enhance the flavor of food with salt, spices, or herbs.', 'season, seasoned, seasoning'),
        ('Simmer', 'To cook food in liquid just below boiling.', 'simmer, simmered, simmering'),
        ('Skim', 'To remove fat or foam from the surface of a liquid.', 'skim, skimmed, skimming'),
        ('Steam', 'To cook food using steam from boiling water.', 'steam, steamed, steaming'),
        ('Steep', 'To soak dry ingredients in liquid to extract flavor.', 'steep, steeped, steeping'),
        ('Temper', 'To gently heat an ingredient (like eggs or chocolate) to prevent curdling or seizing.', 'temper, tempered, tempering'),
        ('Tenderize', 'To make meat more tender by pounding or marinating.', 'tenderize, tenderized, tenderizing'),
        ('Toss', 'To mix ingredients lightly by lifting and dropping them.', 'toss, tossed, tossing'),
        ('Umami', 'A savory flavor, one of the five basic tastes.', 'umami'),
        ('Unprepared', 'The ingredient is in its natural state without any cutting, peeling, cooking, or other preparation steps applied', 'unprepared'),
        ('Whip', 'To beat ingredients to increase volume and incorporate air.', 'whip, whipped, whipping');


    --Measurements
    INSERT INTO glossary
        (term, definition, relatedForms) VALUES('Teaspoon', '(tsp) A small unit of volume, approximately 5 milliliters.', 'teaspoon, teaspoons'),
        ('Tablespoon', '(tbsp) A larger unit of volume, equal to 3 teaspoons or approximately 15 milliliters.', 'tablespoon, tablespoons'),
        ('Cup', 'A unit of volume, equal to 16 tablespoons or 240 milliliters.', 'cup, cups'),
        ('Milliliter', '(ml) A metric unit of volume, equal to one-thousandth of a liter.', 'milliliter, milliliters'),
        ('Liter', '(L) A metric unit of volume, equal to 1,000 milliliters.', 'liter, liters'),
        ('Fluid Ounce', '(fl oz) A unit of liquid volume, approximately 30 milliliters.', 'fluid ounce, fluid ounces'),
        ('Ounce', '(oz) A unit of weight, approximately 28 grams.', 'ounce, ounces'),
        ('Pound', '(lb) A unit of weight, equal to 16 ounces or about 0.45 kilograms.', 'pound, pounds'),
        ('Gram', '(g) A metric unit of weight, equal to one-thousandth of a kilogram.', 'gram, grams'),
        ('Kilogram', '(kg) A metric unit of weight, equal to 1,000 grams or about 2.2 pounds.', 'kilogram, kilograms'),
        ('Pinch', 'A very small amount of a spice or seasoning, taken between the thumb and forefinger.', 'pinch, pinches'),
        ('Dash', 'A small amount of a liquid or seasoning, typically less than 1/8 teaspoon.', 'dash, dashes');

    --Preparation Techniques
    INSERT INTO glossary
        (term, definition, relatedForms) VALUES('Dice', 'To cut food into small, uniform cubes.', 'dice, diced, dicing'),
        ('Mince', 'To finely chop food into very small pieces.', 'mince, minced, mincing'),
        ('Slice', 'To cut food into thin, flat pieces.', 'slice, sliced, slicing'),
        ('Cube', 'To cut food into larger, uniform cubes.', 'cube, cubed, cubing'),
        ('Crush', 'To press or smash food into small fragments.', 'crush, crushed, crushing'),
        ('Grind', 'To reduce food to small particles or powder, typically using a grinder.', 'grind, ground, grinding'),
        ('Mash', 'To crush or blend food into a soft, uniform consistency.', 'mash, mashed, mashing'),
        ('Shake', 'To mix liquids or ingredients by shaking them vigorously.', 'shake, shaken, shaking'),
        ('Stir', 'To mix ingredients gently using a spoon or similar utensil.', 'stir, stirred, stirring'),
        ('Whisk', 'To beat or stir ingredients rapidly to incorporate air.', 'whisk, whisked, whisking'),
        ('Beat', 'To mix ingredients rapidly to achieve a smooth or airy consistency.', 'beat, beaten, beating'),
        ('Peel', 'To remove the outer skin or shell of a fruit or vegetable.', 'peel, peeled, peeling'),
        ('Zest', 'To scrape the colored outer peel of citrus fruits for flavor.', 'zest, zested, zesting'),
        ('Whole', 'To leave an ingredient in its unaltered, complete state.', 'whole'),
        ('Toast', 'To brown food by applying dry heat, often in an oven or toaster.', 'toast, toasted, toasting');

    --Cooking Methods
    INSERT INTO glossary(term, definition, relatedForms) VALUES
        ('Fry', 'To cook food in hot oil or fat.', 'fry, fried, frying'),
        ('Sear', 'To quickly brown the surface of food at high heat.', 'sear, seared, searing'),
        ('Mix', 'To combine ingredients into a uniform mixture.', 'mix, mixed, mixing'),
        ('Assemble', 'To arrange or combine prepared ingredients into a finished dish.', 'assemble, assembled, assembling');




    --Common baking and cooking substitutions
    INSERT INTO commonSubs(
        originalComponent, originalQuantity, originalMeasurement, originalPreparation,
        substitutedComponent, substitutedQuantity, substitutedMeasurement, substitutedPreparation
    )
    VALUES
    --Fats and Oils
        ('Butter', '1', 'Cups', '', 'Coconut Oil', '1', 'Cups', 'Melted'),
        ('Butter', '1', 'Cups', '', 'Olive Oil', '3/4', 'Cups', ''),
        ('Oil', '1', 'Cups', '', 'Mashed Banana', '1/2', 'Cups', ''),
        ('Oil', '1', 'Cups', '', 'Greek Yogurt', '3/4', 'Cups', ''),
        ('Oil', '1', 'Cups', '', 'Applesauce', '3/4', 'Cups', 'Unsweetened'),
        ('Shortening', '1', 'Cups', '', 'Butter', '1', 'Cups', ''),
        ('Butter', '1', 'Tablespoons', '', 'Ghee', '1', 'Tablespoons', ''),

        --Dairy
        ('Milk', '1', 'Cups', '', 'Soy Milk', '1', 'Cups', ''),
        ('Milk', '1', 'Cups', '', 'Oat Milk', '1', 'Cups', ''),
        ('Heavy Cream', '1', 'Cups', '', 'Milk + Butter', '1', 'Cups', 'Combined'),
        ('Cream Cheese', '1', 'Cups', '', 'Ricotta Cheese', '1', 'Cups', ''),
        ('Sour Cream', '1', 'Cups', '', 'Cottage Cheese', '1', 'Cups', 'Blended'),
        ('Buttermilk', '1', 'Cups', '', 'Yogurt + Water', '1', 'Cups', 'Mixed'),
        ('Milk', '1', 'Cups', '', 'Cashew Milk', '1', 'Cups', ''),
        ('Butter', '1', 'Cups', '', 'Avocado', '1', 'Cups', 'Mashed'),

        --Eggs
        ('Egg', '1', 'Whole', '', 'Banana', '1/2', 'Cups', 'Mashed'),
        ('Egg', '1', 'Whole', '', 'Unsweetened Applesauce', '1/4', 'Cups', ''),
        ('Egg', '1', 'Whole', '', 'Silken Tofu', '1/4', 'Cups', 'Blended'),
        ('Egg', '1', 'Whole', '', 'Commercial Egg Replacer', '1', 'Tablespoons', 'Mixed with water'),
        ('Egg', '1', 'Whole', '', 'Yogurt', '1/4', 'Cups', ''),

        --Flours
        ('Flour', '1', 'Cups', '', 'Almond Flour', '1', 'Cups', ''),
        ('Flour', '1', 'Cups', '', 'Oat Flour', '1', 'Cups', 'Blended'),
        ('Flour', '1', 'Cups', '', 'Coconut Flour', '1/4', 'Cups', ''),
        ('Flour', '1', 'Cups', '', 'Rice Flour', '1', 'Cups', ''),
        ('Flour', '1', 'Cups', '', 'Chickpea Flour', '1', 'Cups', ''),

        --Sugars and Sweeteners
        ('Sugar', '1', 'Cups', '', 'Maple Syrup', '3/4', 'Cups', ''),
        ('Sugar', '1', 'Cups', '', 'Agave Nectar', '3/4', 'Cups', ''),
        ('Sugar', '1', 'Cups', '', 'Stevia', '1', 'Teaspoons', 'Powdered'),
        ('Brown Sugar', '1', 'Cups', '', 'Coconut Sugar', '1', 'Cups', ''),
        ('Honey', '1', 'Cups', '', 'Maple Syrup', '1', 'Cups', ''),
        ('Honey', '1', 'Cups', '', 'Molasses', '3/4', 'Cups', ''),

        --Grains and Pasta
        ('Pasta', '1', 'Cups', 'Cooked', 'Spaghetti Squash', '1', 'Cups', 'Cooked'),
        ('Rice', '1', 'Cups', 'Cooked', 'Quinoa', '1', 'Cups', 'Cooked'),
        ('Rice', '1', 'Cups', 'Cooked', 'Barley', '1', 'Cups', 'Cooked'),
        ('Couscous', '1', 'Cups', 'Cooked', 'Cauliflower Rice', '1', 'Cups', 'Grated'),

        --Proteins
        ('Ground Beef', '1', 'Pounds', '', 'Mushrooms', '1', 'Pounds', 'Chopped'),
        ('Ground Chicken', '1', 'Pounds', '', 'Ground Turkey', '1', 'Pounds', ''),
        ('Ground Meat', '1', 'Pounds', '', 'Tofu', '1', 'Pounds', 'Crumbled'),
        ('Ground Beef', '1', 'Pounds', '', 'Black Beans', '1', 'Pounds', 'Cooked'),
        ('Chicken Breast', '1', 'Cups', 'Shredded', 'Jackfruit', '1', 'Cups', 'Shredded'),

        --Thickeners and Binders
        ('Cornstarch', '1', 'Tablespoons', '', 'Arrowroot Powder', '1', 'Tablespoons', ''),
        ('Cornstarch', '1', 'Tablespoons', '', 'Tapioca Starch', '2', 'Tablespoons', ''),
        ('Gelatin', '1', 'Tablespoons', '', 'Agar Agar', '1', 'Tablespoons', ''),

        --Miscellaneous
        ('Salt', '1', 'Teaspoons', '', 'Seaweed Powder', '1', 'Teaspoons', ''),
        ('Soy Sauce', '1', 'Tablespoons', '', 'Coconut Aminos', '1', 'Tablespoons', ''),
        ('Vinegar', '1', 'Tablespoons', '', 'Lemon Juice', '1', 'Tablespoons', ''),
        ('Wine', '1', 'Cups', '', 'Grape Juice + Vinegar', '1', 'Cups', 'Mixed'),
        ('Breadcrumbs', '1', 'Cups', '', 'Crushed Crackers', '1', 'Cups', ''),
        ('Breadcrumbs', '1', 'Cups', '', 'Ground Nuts', '1', 'Cups', ''),
        ('Baking Powder', '1', 'Teaspoons', '', 'Baking Soda + Lemon Juice', '1/2', 'Teaspoons', 'Combined'),
        ('Tomato Sauce', '1', 'Cups', '', 'Tomato Paste + Water', '1/2', 'Cups', 'Mixed'),
        ('Cocoa Powder', '1', 'Tablespoons', '', 'Carob Powder', '1', 'Tablespoons', '');

    --Insert into account table
    INSERT INTO account(email, password, active, verificationCode) VALUES
        ('david33reese@gmail.com', '\$argon2id\$v=19\$m=65536,t=3,p=1\$2qnmXPBW7VUtoy4tPmGe3w\$CopbmJNKnWJ9DklYbSqgjPSYHfPTIvctvmSYidgmBBM', TRUE, 123456),
        ('david598research@gmail.com', '\$argon2id\$v=19\$m=65536,t=3,p=1\$2qnmXPBW7VUtoy4tPmGe3w\$CopbmJNKnWJ9DklYbSqgjPSYHfPTIvctvmSYidgmBBM', TRUE, 123456);


    --Insert into profile table
    INSERT INTO profile(id, firstName, lastName, cookingLevel, favoriteDish, favoriteCuisine) VALUES
        (1, 'David', 'Reese', 'Intermediate', 'Spaghetti Carbonara', 'Italian'),
        (2, 'David', 'Reese', 'Intermediate', 'Enchiladas', 'Mexican');

    --Insert into authority table
    INSERT INTO authority(email, role) VALUES
        ('david33reese@gmail.com', 'ROLE_MOD'),
        ('david33reese@gmail.com', 'ROLE_ADMIN'),
        ('david33reese@gmail.com', 'ROLE_USER'),
        ('david598research@gmail.com', 'ROLE_USER');

    --Insert additional user accounts
    INSERT INTO account(email, password, active, verificationCode) VALUES
        ('alice.smith@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123457),
        ('bob.johnson@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123458),
        ('carol.davis@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123459),
        ('daniel.martin@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123460),
        ('emma.wilson@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123461),
        ('frank.thomas@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123462),
        ('grace.lee@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123463),
        ('henry.white@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123464),
        ('isabella.moore@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123465),
        ('jackson.taylor@example.com', '$argon2id$v=19$m=65536,t=3,p=1$randomhash$', TRUE, 123466);

    --Insert into profile table
    INSERT INTO profile(id, firstName, lastName, cookingLevel, favoriteDish, favoriteCuisine) VALUES
        (3, 'Alice', 'Smith', 'Beginner', 'Chicken Alfredo', 'Italian'),
        (4, 'Bob', 'Johnson', 'Expert', 'Beef Wellington', 'French'),
        (5, 'Carol', 'Davis', 'Novice', 'Vegetable Stir Fry', 'Chinese'),
        (6, 'Daniel', 'Martin', 'Intermediate', 'BBQ Ribs', 'American'),
        (7, 'Emma', 'Wilson', 'Advanced', 'Paella', 'Spanish'),
        (8, 'Frank', 'Thomas', 'Novice', 'Pho', 'Vietnamese'),
        (9, 'Grace', 'Lee', 'Beginner', 'Dumplings', 'Chinese'),
        (10, 'Henry', 'White', 'Expert', 'Sushi', 'Japanese'),
        (11, 'Isabella', 'Moore', 'Intermediate', 'Curry', 'Indian'),
        (12, 'Jackson', 'Taylor', 'Advanced', 'Biryani', 'Middle Eastern');

    --Insert into authority table
    INSERT INTO authority(email, role) VALUES
        ('alice.smith@example.com', 'ROLE_USER'),
        ('bob.johnson@example.com', 'ROLE_USER'),
        ('carol.davis@example.com', 'ROLE_USER'),
        ('daniel.martin@example.com', 'ROLE_USER'),
        ('emma.wilson@example.com', 'ROLE_USER'),
        ('frank.thomas@example.com', 'ROLE_USER'),
        ('grace.lee@example.com', 'ROLE_USER'),
        ('henry.white@example.com', 'ROLE_USER'),
        ('isabella.moore@example.com', 'ROLE_USER'),
        ('jackson.taylor@example.com', 'ROLE_USER');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (3, 'Classic Grilled Burger', 'A juicy grilled burger perfect for any summer BBQ.', '4', 'Patties', 10, 0, 10, 0, 20, 0, TRUE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (1, 'Ground Beef', '1', 'Pounds', 'Ground'),
    (1, 'Salt', '1', 'Teaspoons', 'Unprepared'),
    (1, 'Pepper', '0.5', 'Teaspoons', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (1, 1, 'Form beef into 4 patties and season with salt and pepper.', 'Mixing', NULL, 'Medium'),
    (1, 2, 'Grill patties over medium heat until desired doneness.', 'Grilling', 400, 'Medium');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (1, 4, 5),
    (1, 5, 4);

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (5, 'Loaded BBQ Meal', 'The most epic homemade burgers around.', '6', 'Plates', 20, 0, 30, 0, 50, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (2, 'Beef Patties', '6', 'Whole', 'Grilled'),
    (2, 'BBQ Sauce', '1', 'Cups', 'Unprepared'),
    (2, 'Cheddar Cheese', '6', 'Slices', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (2, 1, 'Prepare all meats and assemble patties.', 'Assembling', NULL, 'Medium'),
    (2, 2, 'Grill meats and baste with BBQ sauce.', 'Grilling', 375, 'High');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (2, 6, 5),
    (2, 7, 4);

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (7, 'Birthday Sprinkle Cupcakes', 'Fun and colorful cupcakes perfect for any birthday party.', '12', 'Cupcakes', 20, 0, 15, 0, 35, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (3, 'All-Purpose Flour', '2', 'Cups', 'Sifted'),
    (3, 'Sugar', '1', 'Cups', 'Unprepared'),
    (3, 'Sprinkles', '0.5', 'Cups', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (3, 1, 'Mix flour and sugar, then gently fold in sprinkles.', 'Mixing', NULL, 'Low'),
    (3, 2, 'Bake cupcakes until golden and set.', 'Baking', 350, 'Medium');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (3, 2, 5),
    (3, 3, 4),
    (3, 4, 5),
    (3, 5, 4),
    (3, 6, 5),
    (3, 7, 4);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (3, 'Holidays', 'Birthday');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending)
    VALUES (8, 'Birthday Bash Cake', 'A rich chocolate cake layered with frosting, designed for celebrations.', '1', 'Cakes', 30, 0, 45, 0, 15, 1, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (4, 'Chocolate Cake Mix', '1', 'Whole', 'Unprepared'),
    (4, 'Buttercream Frosting', '2', 'Cups', 'Whisked'),
    (4, 'Chocolate Chips', '1', 'Cups', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (4, 1, 'Bake the cake following package instructions.', 'Baking', 350, 'Medium'),
    (4, 2, 'Cool completely and apply buttercream frosting.', 'Assembling', NULL, 'Low');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (4, 7, 5),
    (4, 8, 5),
    (4, 9, 4),
    (4, 10, 5),
    (4, 11, 4);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (4, 'Holidays', 'Birthday');


    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (9, 'Crispy Orange Chicken', 'A sweet and tangy orange chicken dish, fried to golden perfection.', '4', 'Servings', 20, 0, 25, 0, 45, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (5, 'Chicken Breast', '1', 'Pounds', 'Cubed'),
    (5, 'Orange Juice', '0.5', 'Cups', 'Unprepared'),
    (5, 'Cornstarch', '0.25', 'Cups', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (5, 1, 'Coat chicken cubes with cornstarch.', 'Mixing', NULL, 'Low'),
    (5, 2, 'Fry chicken pieces until crispy.', 'Frying', 375, 'High'),
    (5, 3, 'Toss fried chicken in orange sauce.', 'Mixing', NULL, 'Low');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (5, 2, 5),
    (5, 3, 4);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (5, 'Cuisines', 'Chinese');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (10, 'Sizzling Stir-Fry Delight', 'Loaded with tender chicken slices and crisp veggies in a savory sauce.', '4', 'Servings', 15, 0, 15, 0, 30, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (6, 'Chicken Thighs', '1', 'Pounds', 'Sliced'),
    (6, 'Broccoli', '1', 'Cups', 'Chopped'),
    (6, 'Soy Sauce', '0.25', 'Cups', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (6, 1, 'Sear chicken slices in a hot wok.', 'Searing', NULL, 'High'),
    (6, 2, 'Add vegetables and sauce; stir-fry quickly.', 'Stirring', NULL, 'High');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (6, 1, 5),
    (6, 4, 4);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (6, 'Cuisines', 'Chinese');


    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (11, 'Easy Baked Fish Fillet', 'A simple and healthy baked fish recipe perfect for busy nights.', '2', 'Finished Dishes', 10, 0, 20, 0, 30, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (7, 'White Fish Fillet', '2', 'Whole', 'Unprepared'),
    (7, 'Lemon Juice', '2', 'Tablespoons', 'Unprepared'),
    (7, 'Olive Oil', '1', 'Tablespoons', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (7, 1, 'Drizzle fish fillets with olive oil and lemon juice.', 'Mixing', NULL, 'Low'),
    (7, 2, 'Bake in oven until flaky and cooked through.', 'Baking', 375, 'Medium');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (7, 5, 4),
    (7, 7, 5);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (7, 'Cooking Levels', 'Novice'),
    (7, 'Meal Types', 'Dinner');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (12, 'Herb Lemon Dinner', 'A refreshing herb-seasoned fish dish served with a side salad.', '2', 'Plates', 15, 0, 20, 0, 35, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (8, 'Salmon Fillet', '2', 'Whole', 'Unprepared'),
    (8, 'Fresh Herbs', '0.5', 'Cups', 'Chopped'),
    (8, 'Lemon Zest', '1', 'Tablespoons', 'Zested');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (8, 1, 'Season salmon with herbs and lemon zest.', 'Mixing', NULL, 'Low'),
    (8, 2, 'Roast in oven until tender.', 'Roasting', 400, 'Medium');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (8, 6, 5),
    (8, 8, 5);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (8, 'Cooking Levels', 'Novice'),
    (8, 'Meal Types', 'Dinner');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (3, 'Tiramisu Delight', 'A creamy and coffee-flavored Italian dessert layered with mascarpone cheese.', '1', 'Cakes', 30, 0, 0, 0, 30, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (9, 'Mascarpone Cheese', '1', 'Cups', 'Unprepared'),
    (9, 'Espresso', '0.5', 'Cups', 'Unprepared'),
    (9, 'Ladyfingers', '20', 'Whole', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (9, 1, 'Dip ladyfingers in espresso.', 'Mixing', NULL, 'Low'),
    (9, 2, 'Layer mascarpone mixture with soaked ladyfingers.', 'Assembling', NULL, 'Low'),
    (9, 3, 'Chill in refrigerator until set.', 'Chilling', NULL, 'Low');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (9, 2, 5),
    (9, 3, 5),
    (9, 4, 5),
    (9, 5, 5),
    (9, 6, 5),
    (9, 7, 5);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (9, 'Meal Types', 'Dessert'),
    (9, 'Cuisines', 'Italian');

    INSERT INTO info (userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (4, 'Ricotta Cannoli Bites', 'Mini cannoli shells stuffed with a sweetened ricotta filling.', '24', 'Pieces', 20, 0, 0, 0, 20, 0, FALSE);

    INSERT INTO ingredient (recipeId, component, quantity, measurement, preparation) VALUES
    (10, 'Ricotta Cheese', '2', 'Cups', 'Whisked'),
    (10, 'Powdered Sugar', '1', 'Cups', 'Sifted'),
    (10, 'Mini Cannoli Shells', '24', 'Whole', 'Unprepared');

    INSERT INTO direction (recipeId, stepNum, direction, method, temp, level) VALUES
    (10, 1, 'Whisk ricotta and powdered sugar until smooth.', 'Whisking', NULL, 'Low'),
    (10, 2, 'Fill mini cannoli shells with sweetened ricotta.', 'Assembling', NULL, 'Low');

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (10, 3, 5),
    (10, 2, 5),
    (10, 4, 5),
    (10, 5, 5),
    (10, 6, 5),
    (10, 7, 5);

    INSERT INTO tags (recipeId, tagField, tagValue) VALUES
    (10, 'Meal Types', 'Dessert'),
    (10, 'Cuisines', 'Italian');

    INSERT INTO info (recipeId, userId, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr, staffTrending) VALUES
    (11, 1, 'Tiramisu', 'Classic Italian coffee-flavored dessert.', '8', 'Slices', 20, 0, 0, 0, 20, 0, true),
    (12, 1, 'Chocolate Mousse', 'Rich and creamy chocolate mousse.', '4', 'Cups', 15, 0, 0, 0, 15, 0, false),
    (13, 1, 'Spaghetti Carbonara', 'Traditional Roman pasta with pancetta.', '2', 'Plates', 10, 0, 15, 0, 25, 0, true),
    (14, 1, 'Lemon Tart', 'Zesty and sweet lemon tart with crisp crust.', '6', 'Slices', 30, 0, 20, 0, 50, 0, false),
    (15, 1, 'Summer Salad', 'Fresh mixed greens with seasonal fruits.', '2', 'Bowls', 10, 0, 0, 0, 10, 0, true);

    INSERT INTO rating (recipeId, ratingUserId, ratingValue) VALUES
    (11, 2, 5), (11, 3, 4), (11, 4, 5), (11, 5, 5), (11, 6, 4), (11, 7, 5),
    (12, 2, 5), (12, 3, 4), (12, 4, 4), (12, 5, 5), (12, 6, 5), (12, 7, 4),
    (13, 2, 5), (13, 3, 5), (13, 4, 5), (13, 5, 5), (13, 6, 4), (13, 7, 5),
    (14, 2, 4), (14, 3, 4), (14, 4, 5), (14, 5, 5), (14, 6, 4), (14, 7, 5),
    (15, 2, 5), (15, 3, 4), (15, 4, 4), (15, 5, 5), (15, 6, 4), (15, 7, 5);

    alter sequence info_recipeId_seq restart with 20;
    alter sequence account_id_seq restart with 20;

    alter table account owner to docker;
    alter table authority owner to docker;
    alter table profile owner to docker;
    alter table info owner to docker;
    alter table variation owner to docker;
    alter table direction owner to docker;
    alter table ingredient owner to docker;
    alter table comment owner to docker;
    alter table rating owner to docker;
    alter table tags owner to docker;
    alter table photo owner to docker;
    alter table link owner to docker;
    alter table note owner to docker;
    alter table bookmark owner to docker;
    alter table userSubs owner to docker;
    alter table ticket owner to docker;
    alter table commonSubs owner to docker;
    alter table glossary owner to docker;

EOSQL
