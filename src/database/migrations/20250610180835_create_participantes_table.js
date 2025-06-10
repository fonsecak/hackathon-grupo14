/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function (knex) {
    return knex.schema.createTable('participantes', function(table) {
    table.increments('id').primary();
    table.string('nome').notNullable();
    table.string('sobrenome').notNullable();
    table.string('cpf').unique().notNullable();
    table.string('email').unique().notNullable();
    table.string('senha').notNullable();
    table.string('empresa');
    table.timestamp('created_at').defaultTo(knex.fn.now());
    table.timestamp('updated_at').defaultTo(knex.fn.now());
  });

};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function (knex) {
    return knex.schema.dropTable('participantes');
};
