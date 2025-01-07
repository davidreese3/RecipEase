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
        yield numeric,
        unitOfYield varchar(20), --change once prepop made
        prepMin int,
        prepHr int,
        processMin int,
        processHr int,
        totalMin int, -- trigger
        totalHr int,
        primary key (recipeId)
    );

    -- ingredient
        create table if not exists ingredient (
            recipeId int references info(recipeId) on delete cascade,
            component varchar(30),
            wholeNumberQuantity int,
            fractionQuantity varchar(4),
            measurement varchar(20), --change once prepop made
            preparation varchar(20), --change once prepop made
            primary key (recipeId, component)
        );

    -- direction
    create table if not exists direction (
        recipeId int references info(recipeId) on delete cascade,
        stepNum int,
        direction varchar(300),
        method varchar(30), --change once prepop made
        temp int,
        level varchar(10), --change once prepop made
        primary key(recipeId, stepNum)
    );

    -- =============================
    -- Recipe Community Tables
    -- =============================

    -- comment
    create table if not exists comment (
        recipeId int references info(recipeId) on delete cascade,
        commentId serial, --used for PK
        commentUserId int references account(id) on delete cascade on update cascade, --technically do they link?
        commentText varchar(500),
        primary key(recipeId, commentID)
    );

    --rating
    create table if not exists rating (
        recipeId int references info(recipeId) on delete cascade,
        ratingUserId int references account(id) on delete cascade on update cascade, --technically do they link?
        ratingValue int,
        primary key (recipeId, ratingUserId)
    );

    -- =============================
    -- Recipe Option Fields Tables
    -- =============================

    -- ======
    -- Categories
    -- ======

    -- holiday
    create table if not exists holiday (
        recipeId int references info(recipeId) on delete cascade,
        holiday varchar(30),
        primary key (recipeId, holiday)
    );

    -- meal type
    create table if not exists mealType (
        recipeId int references info(recipeId) on delete cascade,
        mealType varchar(30),
        primary key (recipeId, mealType)
    );

    -- cuisine
    create table if not exists cuisine (
        recipeId int references info(recipeId) on delete cascade,
        cuisine varchar(30),
        primary key (recipeId, cuisine)
    );

    -- allergen
    create table if not exists allergen (
        recipeId int references info(recipeId) on delete cascade,
        allergen varchar(30),
        primary key (recipeId, allergen)
    );

    -- diet type
    create table if not exists dietType (
        recipeId int references info(recipeId) on delete cascade,
        dietType varchar(30),
        primary key (recipeId, dietType)
    );

    -- cooking level
    create table if not exists cookingLevel (
        recipeId int references info(recipeId) on delete cascade,
        cookingLevel varchar(30),
        primary key (recipeId, cookingLevel)
    );

    -- cooking style
    create table if not exists cookingStyle (
        recipeId int references info(recipeId) on delete cascade,
        cookingStyle varchar(30),
        primary key (recipeId, cookingStyle)
    );

    -- ======
    -- Media
    -- ======

    -- photo
    create table if not exists photo (
        recipeId int references info(recipeId) on delete cascade,
        fileLocation varchar(125),
        primary key (recipeId)
    );

    -- link
    create table if not exists link (
        recipeId int references info(recipeId) on delete cascade,
        link varchar(125),
        primary key (recipeId)
    );


    -- ======
    -- Other
    -- ======

    -- notes
    create table if not exists notes (
        recipeId int references info(recipeId) on delete cascade,
        notes varchar(2500),
        primary key (recipeId)
    );

    -- user substitutions
    create table if not exists userSubs (
        recipeId int references info(recipeId) on delete cascade,
        originalComponent varchar(30),
        originalWholeNumberQuantity int,
        originalFractionQuantity varchar(4),
        originalMeasurement varchar(20), --change once prepop made
        originalPreparation varchar(20), --change once prepop made
        substitutedComponent varchar(30),
        substitutedWholeNumberQuantity int,
        substitutedFractionQuantity varchar(4),
        substitutedMeasurement varchar(20), --change once prepop made
        substitutedPreparation varchar(20), --change once prepop made
        primary key(recipeId, originalComponent, substitutedComponent)
    );

    -- =============================
    -- Recipe Set Up Tables
    -- =============================

    -- knownSubs
    create table if not exists knownSubs (
        originalComponent varchar(30),
        originalWholeNumberQuantity int,
        originalFractionQuantity varchar(4),
        originalMeasurement varchar(20), --change once prepop made
        originalPreparation varchar(20), --change once prepop made
        substitutedComponent varchar(30),
        substitutedWholeNumberQuantity int,
        substitutedFractionQuantity varchar(4),
        substitutedMeasurement varchar(20), --change once prepop made
        substitutedPreparation varchar(20), --change once prepop made
        primary key(originalComponent, substitutedComponent)
    );

    -- glossary
    create table if not exists glossary (
        term varchar(30),
        definition varchar(300),
        relatedForms varchar(100),
        primary key(term, definition)
    );

    -- Insert glossary terms and definitions
    INSERT INTO glossary (term, definition, relatedForms) VALUES
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
    ('Unprepared', 'The ingredient is in its natural state without any cutting, peeling, cooking, or other preparation steps applied','unprepared'),
    ('Whip', 'To beat ingredients to increase volume and incorporate air.', 'whip, whipped, whipping');


    -- Measurements
    INSERT INTO glossary (term, definition, relatedForms) VALUES
        ('Teaspoon', '(tsp) A small unit of volume, approximately 5 milliliters.', 'teaspoon, teaspoons'),
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

    -- Preparation Techniques
    INSERT INTO glossary (term, definition, relatedForms) VALUES
        ('Dice', 'To cut food into small, uniform cubes.', 'dice, diced, dicing'),
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

    -- Cooking Methods
    INSERT INTO glossary (term, definition, relatedForms) VALUES
        ('Fry', 'To cook food in hot oil or fat.', 'fry, fried, frying'),
        ('Sear', 'To quickly brown the surface of food at high heat.', 'sear, seared, searing'),
        ('Mix', 'To combine ingredients into a uniform mixture.', 'mix, mixed, mixing'),
        ('Assemble', 'To arrange or combine prepared ingredients into a finished dish.', 'assemble, assembled, assembling');




    -- Common baking and cooking substitutions
    INSERT INTO knownSubs (
        originalComponent, originalWholeNumberQuantity, originalFractionQuantity, originalMeasurement, originalPreparation,
        substitutedComponent, substitutedWholeNumberQuantity, substitutedFractionQuantity, substitutedMeasurement, substitutedPreparation
    )
    VALUES
        -- Fats and Oils
        ('Butter', 1, '0', 'Cups', '', 'Coconut Oil', 1, '0', 'Cups', 'Melted'),
        ('Butter', 1, '0', 'Cups', '', 'Olive Oil', 0, '3/4', 'Cups', ''),
        ('Oil', 1, '0', 'Cups', '', 'Mashed Banana', 0, '1/2', 'Cups', ''),
        ('Oil', 1, '0', 'Cups', '', 'Greek Yogurt', 0, '3/4', 'Cups', ''),
        ('Oil', 1, '0', 'Cups', '', 'Applesauce', 0, '3/4', 'Cups', 'Unsweetened'),
        ('Shortening', 1, '0', 'Cups', '', 'Butter', 1, '0', 'Cups', ''),
        ('Butter', 1, '0', 'Tablespoons', '', 'Ghee', 1, '0', 'Tablespoons', ''),

        -- Dairy
        ('Milk', 1, '0', 'Cups', '', 'Soy Milk', 1, '0', 'Cups', ''),
        ('Milk', 1, '0', 'Cups', '', 'Oat Milk', 1, '0', 'Cups', ''),
        ('Heavy Cream', 1, '0', 'Cups', '', 'Milk + Butter', 1, '0', 'Cups', 'Combined'),
        ('Cream Cheese', 1, '0', 'Cups', '', 'Ricotta Cheese', 1, '0', 'Cups', ''),
        ('Sour Cream', 1, '0', 'Cups', '', 'Cottage Cheese', 1, '0', 'Cups', 'Blended'),
        ('Buttermilk', 1, '0', 'Cups', '', 'Yogurt + Water', 1, '0', 'Cups', 'Mixed'),
        ('Milk', 1, '0', 'Cups', '', 'Cashew Milk', 1, '0', 'Cups', ''),
        ('Butter', 1, '0', 'Cups', '', 'Avocado', 1, '0', 'Cups', 'Mashed'),

        -- Eggs
        ('Egg', 1, '0', 'Whole', '', 'Banana', 0, '1/2', 'Cups', 'Mashed'),
        ('Egg', 1, '0', 'Whole', '', 'Unsweetened Applesauce', 0, '1/4', 'Cups', ''),
        ('Egg', 1, '0', 'Whole', '', 'Silken Tofu', 0, '1/4', 'Cups', 'Blended'),
        ('Egg', 1, '0', 'Whole', '', 'Commercial Egg Replacer', 1, '0', 'Tablespoons', 'Mixed with water'),
        ('Egg', 1, '0', 'Whole', '', 'Yogurt', 0, '1/4', 'Cups', ''),

        -- Flours
        ('White Flour', 1, '0', 'Cups', '', 'Almond Flour', 1, '0', 'Cups', ''),
        ('White Flour', 1, '0', 'Cups', '', 'Oat Flour', 1, '0', 'Cups', 'Blended'),
        ('White Flour', 1, '0', 'Cups', '', 'Coconut Flour', 0, '1/4', 'Cups', ''),
        ('White Flour', 1, '0', 'Cups', '', 'Rice Flour', 1, '0', 'Cups', ''),
        ('White Flour', 1, '0', 'Cups', '', 'Chickpea Flour', 1, '0', 'Cups', ''),

        -- Sugars and Sweeteners
        ('Sugar', 1, '0', 'Cups', '', 'Maple Syrup', 0, '3/4', 'Cups', ''),
        ('Sugar', 1, '0', 'Cups', '', 'Agave Nectar', 0, '3/4', 'Cups', ''),
        ('Sugar', 1, '0', 'Cups', '', 'Stevia', 1, '0', 'Teaspoons', 'Powdered'),
        ('Brown Sugar', 1, '0', 'Cups', '', 'Coconut Sugar', 1, '0', 'Cups', ''),
        ('Honey', 1, '0', 'Cups', '', 'Maple Syrup', 1, '0', 'Cups', ''),
        ('Honey', 1, '0', 'Cups', '', 'Molasses', 0, '3/4', 'Cups', ''),

        -- Grains and Pasta
        ('Pasta', 1, '0', 'Cups', 'Cooked', 'Spaghetti Squash', 1, '0', 'Cups', 'Cooked'),
        ('Rice', 1, '0', 'Cups', 'Cooked', 'Quinoa', 1, '0', 'Cups', 'Cooked'),
        ('Rice', 1, '0', 'Cups', 'Cooked', 'Barley', 1, '0', 'Cups', 'Cooked'),
        ('Couscous', 1, '0', 'Cups', 'Cooked', 'Cauliflower Rice', 1, '0', 'Cups', 'Grated'),

        -- Proteins
        ('Ground Beef', 1, '0', 'Pounds', '', 'Mushrooms', 1, '0', 'Pounds', 'Chopped'),
        ('Ground Chicken', 1, '0', 'Pounds', '', 'Ground Turkey', 1, '0', 'Pounds', ''),
        ('Ground Meat', 1, '0', 'Pounds', '', 'Tofu', 1, '0', 'Pounds', 'Crumbled'),
        ('Ground Beef', 1, '0', 'Pounds', '', 'Black Beans', 1, '0', 'Pounds', 'Cooked'),
        ('Chicken Breast', 1, '0', 'Cups', 'Shredded', 'Jackfruit', 1, '0', 'Cups', 'Shredded'),

        -- Thickeners and Binders
        ('Cornstarch', 1, '0', 'Tablespoons', '', 'Arrowroot Powder', 1, '0', 'Tablespoons', ''),
        ('Cornstarch', 1, '0', 'Tablespoons', '', 'Tapioca Starch', 2, '0', 'Tablespoons', ''),
        ('Gelatin', 1, '0', 'Tablespoons', '', 'Agar Agar', 1, '0', 'Tablespoons', ''),

        -- Miscellaneous
        ('Salt', 1, '0', 'Teaspoons', '', 'Seaweed Powder', 1, '0', 'Teaspoons', ''),
        ('Soy Sauce', 1, '0', 'Tablespoons', '', 'Coconut Aminos', 1, '0', 'Tablespoons', ''),
        ('Vinegar', 1, '0', 'Tablespoons', '', 'Lemon Juice', 1, '0', 'Tablespoons', ''),
        ('Wine', 1, '0', 'Cups', '', 'Grape Juice + Vinegar', 1, '0', 'Cups', 'Mixed'),
        ('Breadcrumbs', 1, '0', 'Cups', '', 'Crushed Crackers', 1, '0', 'Cups', ''),
        ('Breadcrumbs', 1, '0', 'Cups', '', 'Ground Nuts', 1, '0', 'Cups', ''),
        ('Baking Powder', 1, '0', 'Teaspoons', '', 'Baking Soda + Lemon Juice', 0, '1/2', 'Teaspoons', 'Combined'),
        ('Tomato Sauce', 1, '0', 'Cups', '', 'Tomato Paste + Water', 0, '1/2', 'Cups', 'Mixed'),
        ('Cocoa Powder', 1, '0', 'Tablespoons', '', 'Carob Powder', 1, '0', 'Tablespoons', '');



    -- Insert into account table
    INSERT INTO account (email, password, active, activationCode) VALUES
    ('david33reese@gmail.com', '\$2b\$12\$FaFSKjH6oBrIk3JxHimLfuK2Q.QCqEczCn92.bX3pN/cly71SCNv6', TRUE, 123456),
    ('frank_reese@yahoo.com', '\$2b\$12\$FaFSKjH6oBrIk3JxHimLfuK2Q.QCqEczCn92.bX3pN/cly71SCNv6', TRUE, 123456);


    -- Insert into profile table
    INSERT INTO profile (id, firstName, lastName, cookingLevel, favoriteDish, favoriteCuisine) VALUES
      (1, 'David', 'Reese', 'Intermediate', 'Spaghetti Carbonara', 'Italian'),
      (2, 'Frank', 'Reese', 'Intermediate', 'Enchiladas', 'Mexican');

    -- Insert into authority table
    INSERT INTO authority (email, role) VALUES
      ('david33reese@gmail.com', 'ROLE_USER'),
      ('frank_reese@yahoo.com', 'ROLE_USER');



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
    alter table cookingLevel owner to docker;
    alter table cookingStyle owner to docker;
    alter table photo owner to docker;
    alter table link owner to docker;
    alter table notes owner to docker;
    alter table userSubs owner to docker;
    alter table knownSubs owner to docker;
    alter table glossary owner to docker;

EOSQL
