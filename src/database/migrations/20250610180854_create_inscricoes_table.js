/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function (knex) {
    return knex.schema.createTable('inscricoes', function (table) {
        table.increments('id').primary();
        table.integer('id_participante').unsigned().notNullable()
            .references('id').inTable('participantes').onDelete('CASCADE');
        table.integer('id_evento').unsigned().notNullable()
            .references('id').inTable('eventos').onDelete('CASCADE');
        table.timestamp('created_at').defaultTo(knex.fn.now());
        table.boolean('status').defaultTo(false);
        table.unique(['id_participante', 'id_evento']); // Garantir que um participante só possa se inscrever uma vez em um evento
    });
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function (knex) {
    return knex.schema.dropTable('inscricoes');
};
