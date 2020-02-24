const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

const MAX_MESSAGES = 300;

exports.limitNumberOfMessages = functions.database.ref('/chats/{villageId}')
	.onWrite(async (change) => {
		const messagesRef = change.after.ref;
		const snapshot = await messagesRef.once('value');
		
		if (snapshot.numChildren() >= MAX_MESSAGES) {
			let childCount = 0;
			const updates = {};
			
			snapshot.forEach((child) => {
			  if (++childCount <= snapshot.numChildren() - MAX_MESSAGES) {
				updates[child.key] = null;
			  }
			});

			return messagesRef.update(updates);
		 }

		return null;
	});